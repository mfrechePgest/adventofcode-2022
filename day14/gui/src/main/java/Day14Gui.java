import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.io.IOException;
import java.nio.IntBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Day14Gui {

    public static final int ANIMATION_DURATION = 2000;
    public static final String TITLE = "--- Day 14: Regolith Reservoir ---";
    // The window handle
    private long window;
    private Day14 event;
    private List<CellDisplay> cells = null;
    private List<CellAnimation> animations = null;
    private List<Object> movements = new LinkedList<>();
    private float largeurCase = 0f;

    private boolean animate = false;
    private boolean resizeAnimation = false;
    private Long animationStart = null;
    Map<Coord, Matter> currentSituation = null;
    private Coord previousSandPosition = Day14.SAND_DROP_ORIGIN;


    private final ImageParser resource_01 = ImageParser.load_image("logo_waterfall.png");


    public static void main(String[] args) throws IOException {
        new Day14Gui().run();
    }

    public void run() throws IOException {
        System.out.println(TITLE);


        event = new Day14(this::computeMovements);
        event.readFile();

        initSituation();

        init();

        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void computeMovements(Map<Coord, Matter> grid, Coord coord) {
        // TODO Ajouter des CellAnimation à la liste
        if (coord.equals(Day14.SAND_DROP_ORIGIN)) {
            // On a lancé un nouveau grain de sable, pas de movement mais on redessine
            // TODO resize
            initSituation();
        } else {
            movements.add(new CellAnimation(new CellDisplay(previousSandPosition, largeurCase), new CellDisplay(coord, largeurCase)));
            previousSandPosition = coord;
        }
    }

    private void initSituation() {
        currentSituation = event.grid;
        largeurCase = computeLargeurCase(event.highestX - event.lowestX, event.highestY - event.lowestY);
        cells = currentSituation.keySet().stream()
                .map(dot -> new CellDisplay(dot, largeurCase, dot.x() - event.lowestX, dot.y() - event.lowestX))
                .toList();
    }

    private float computeLargeurCase(int maxX, int maxY) {
        return (0.9f * 2) / Math.max(maxX, maxY);
    }


    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE,
                GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(1024, 768, TITLE, NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
            } else {
                if (key == GLFW_KEY_SPACE && action == GLFW_RELEASE && !animate && !movements.isEmpty()) {
                    animations.add((CellAnimation) movements.remove(0));
                }
            }
        });

        GLFWImage image = GLFWImage.malloc();
        GLFWImage.Buffer imagebf = GLFWImage.malloc(1);
        image.set(resource_01.get_width(), resource_01.get_heigh(), resource_01.get_image());
        imagebf.put(0, image);
        glfwSetWindowIcon(window, imagebf);

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2);
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        glfwSetWindowSizeCallback(window, new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                glViewport(0, 0, width, height);
            }
        });

        // Make the window visible
        glfwShowWindow(window);
    }


    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.2f, 0.0f);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            if (animate) {
                if (animationStart == null) {
                    animationStart = System.currentTimeMillis();
                }
                float timeElapsedProportion = (System.currentTimeMillis() - animationStart) / (float) ANIMATION_DURATION;
                animations.forEach(anim -> anim.getCurrentCellDisplay(timeElapsedProportion).draw());
                if (!resizeAnimation) {
                    // Si on est pendant un folding : on dessine aussi les points qui bougent pas
//                    cells.stream()
//                            .filter(cell -> !cell.getDot().shouldMoveDuringFolding(event.getNextFoldingInstruction()))
//                            .forEach(CellDisplay::draw);
                }
            } else {
                cells.stream()
                        .filter(display -> display.getOriginX() > 0 && display.getOriginY() > 0) // dans le champ
                        .filter(display -> display.getDot().x() <= event.highestX && display.getDot().y() <= event.highestY)
                        .forEach(CellDisplay::draw);
            }

            if (animate && System.currentTimeMillis() - animationStart > ANIMATION_DURATION) {
                if (!movements.isEmpty()) {
                    Object nextMovement = movements.get(0);
                    if (nextMovement instanceof CellAnimation anim) {
                        animations.add(anim);
                    } else if (nextMovement instanceof Resize resize) {
                        // resize
                        resizeAnimation = true;
                        animations = cells.stream()
                                .map(dot -> new CellAnimation(
                                        new CellDisplay(dot.getDot(), computeLargeurCase(resize.previousMaxX() - resize.previousMinX(), resize.previousMaxY() - resize.previousMinY())),
                                        dot
                                )).toList();
                    }
                }
            } else {
                animate = false;
                animations = null;
                resizeAnimation = false;
            }

            animationStart = null;
        }

        glfwSwapBuffers(window); // swap the color buffers

        // Poll for window events. The key callback above will only be
        // invoked during this call.
        glfwPollEvents();
    }
}

