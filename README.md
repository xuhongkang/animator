## Animator PLUS Project Description
CS3500: Object-Oriented Design

Instructor: Clark Freifeld

Design & Development: Hongkang Xu

Build & Testing: Shengjun Sun

Collaborators: Andrew Briasco-Stewart, Allison Cross

### ASSIGNMENT 8 DESCRIPTION:
For this assignment, we had to implement a new add-layer 
functionality as well as a new realtime add-shape/motion/layer functionality
for the interactive view. We decided to approach these issues in the following ways:
1 Level 1: New shape and UI improvements

In this level, we add support for a new shape: the plus sign. The plus sign contains a horizontal and vertical bar with a common center. By default, one can imagine the plus to be tightly enclosed with a square. The vertical sides of the horizontal bar should be half the height of this square, while the horizontal sides of the vertical bar should be half the width of this square. In other words, the dimensions of the plus are relative to the box that encloses it.

Our program should not only support drawing this shape, but also being able to move or scale it (scaling the dimensions of the shape amounts to scaling the bounding square as explained above, and the proportions above should be retained).

In addition to this, this level must also support a new UI feature. One should be able to toggle the drawing of all shapes in ''fill" vs ''outline" mode. In outline mode, each shape should be drawn only using its outlines (the plus symbol should have its proper outline, no ''square in the middle). The color of the outline should match the color of the shape, but you can use any suitable value for the thickness of the outline.

Completion of this feature will be evidenced by the following features in our program:

- The ability for a user to toggle between outline and fill modes using the user interface only in the interactive view.
- The ability of our program to show the new shape in an animation in your interactive view.
- The ability of our program to show the new shape correctly in an SVG file produced by your program.
- The ability of our program to accept this shape as part of an input file: this must be an extension , not a replacement , of the existing file format.

2 Level 2: Discrete-time playing of animation
Sometimes it is quicker to view an entire animation using a few discrete frames, rather than play the entire animation at the given speed. An example of this may be when we fast-forward a movie on Netflix, and watch individual frames go by rather than seeing a smooth but fast play.

In this tier we will add such a discrete way of playing your animation in the interactive view. More specifically, our view should display only the frames at the start and the end of any motion. If these times happen to be in the middle of other motions, the frame should show the correct interpolated states for those shapes. You may play these discrete frames at a suitable tempo: it is not necessary to be able to interactively increase and decrease the speed of this discrete playing.

Completion of this feature will be evidenced by the following features in our program:

- The ability to start, pause, resume and stop the discrete playing of an animation.
- The ability to switch between discrete and continuous playing of an animation without having to restart the program or reload the animation.

Retain all existing abilities of our interactive view.

3 Level 3: Slo-mo animation
Creating a ''slo-mo" video is a feature on many smart phones. The user can record a video normally, and then edit the video so that different parts of it can be played at different speeds. This allows the viewer to see an important part of the video in slow motion, adding focus on that part. In this part, we must enhance our program to play an animation in slow motion snippets similarly.

Completion of this feature will be evidenced by the following features in our program:

The ability of an input file to take in the slow-motion information. Specifically we will have to specify several (time-interval: tempo) instances for an animation. Since the tempo information is currently not part of the input files for the animation, we have two options. We can either add this information to the existing input file format, or we can add this in a separate file and load it separately for an animation.

The ability of our program to play the animation in slow motion according to the inputs, in our interactive view. We do not have to support this feature for the other views, including the visual view.

### ASSIGNMENT 7 DESCRIPTION:
1. Add-Layer
A new layer must select a group of shapes to add. The model must store the names of these
layers and draw them according the order they were created. Also, shapes that were not
assigned to a new layer must be put into a "default" layer and be drawn on the bottom.
Thus, we decided to change the Animator-File-Reader class that was present in the io folder.

In the Animator-File-Reader, we added the option to read "layer" commands. We define layer
commands as: Starting with the key word "layer", followed by a string that represents the name
of the layer. After that, you may add all the IDs of shapes you would like to belong to this 
layer, separated by spaces. Layer commands can be assigned anywhere in the input text file,
so the shapeIDs that are present in the command do not need to be initialized during the time
the command is created. Thus, we also do not check for invalid shapeIDs. In case of 
invalid shapeIDs (shapes never created), the view will not display them. It is recommended
that the layer command be declared near the top of the file under the canvas dimensions command.

Example: In "buildings.txt":
canvas 800 800
layer middle B0 B1
layer top window000
...

The above commands will put the two buildings on the left on the "middle" layer, covering the windows
on the "default" layer (since they will not be assigned to a layer). However, one window at the 
bottom left corner of building 0 (leftest building) will be placed on the "top" layer. Since the "top"
layer is declared after the "middle" layer, it is placed above. Thus, this window will be visible,
despite the building being in the "middle" layer.

Apart from changing Animator-File-Reader, we added an addLayer() method that takes in a layer name as well
as a list of commands via the file reader, and saves them in a linkedHashMap that saves their order.
When paintComponent() is called in AnimatorPanel, we call getShapes() from the model. We modified this
method so that it not only gets the existing shapes, but also returns them in the correct order of the 
layers they were assigned to. Default shapes are passed first, so that they are drawn first. Layers that
are declared first while be on the top, and so they are drawn last and overwrites any other overlapping
shapes.

2. Add-Shape/Motion
In InteractiveAnimatorView, we added a JTextField in which users can type in commands realtime. We also 
added a JButton "add" which can be pressed after the user finishes their input. This JButton calls an
actionListener (the same class as originally implemented) which triggers addNewCommand() in the 
InteractiveController. This method attempts to create a new "TemporaryCommands.txt" file and write the 
command to this file.

When paintComponent() is called, it first reads the file using Animator-File-Reader, then it updates the 
model to its current state and deletes the file once the action is performed before drawing the animations. 
If an invalid command is added, this will mean that the Animator-File-Reader will throw an exception. 
the exception is caught in paintComponent(), and reverts the model back to a "backup" state before the new
command was attempting to be added. Thus, the model will keep running alongside the awt thread, and invalid
commands will not be added. Thus, any existing command in the previous implementation can now be run and
"edited upon" in realtime in this implementation.

### ASSIGNMENT 6 DESCRIPTION / FILE LIST:
Changelog from assignment 5:
- Enhanced main method (RunAnimation) to support the new interactive view.
- Enhanced AnimatorViewCreator to add ability to create interactive view.
- Fixed bug where first animation per type for a shape didn't check against the starting shape values
- Fixed bug where error box would pop up in RunAnimation if now output file was specified
  (it should just write to System.Out)
- Fixed bug with SVG animation getting the shapes in the wrong order in its toString Method.
- Brought TextAnimatorView function out of the Model and put into the View. The TextAnimatorView now
  relies on the same methods as the SVG animator and there are no view specific functions in the model.
- Changed some interface/class names to new names (eg. ComplexAnimatorView -> SimpleAnimatorView)
- Extended SimpleAnimatorView with new methods, previous view doesn't support them and does nothing.



High-level Description:
 Previous descriptions still apply. This assignment resulted in the enhancement of the simple
 controller from the previous assignment. This controller supports several features that the connected
 view can use to tell the controller to: speed-up, slow-down, loop, restart, pause, play. A new view
 that uses these features was created (InteractiveAnimatorView). The methods are passed via a setCallbacks
 method that the controller calls on the underlying view to provide the methods. The Interactive view
 has several buttons and a checkbox to support desired functionality, and also implements ActionListener
 to act when those elements are interacted with. Each element has an identifying string and the view
 maps each string to the desired function from the controller to produce the desired result.

 In this assignment we also created two custom animations. The first is algorithmically generated with
 the AlgoInsertSortAnimation main method. This class generates a visual insert sort animation and
 puts the text into a file called insert-sort-animation.txt inside the resources folder. The
 algorithm has many constants defined at the beginning of the file to customize the size/speed of
 the animation. The output file can be read by the PlayAnimation main method to produce any of the
 desired formats.

 The second animation is space cat. It is a recreation of nyan cat. This was created by hand and
 can be read/run with the PlayAnimation main method to produce any of the desired formats.

NEW DESCRIPTIONS. Old file descriptions located below in past assignment descriptions.
New Interface Description:
 AnimatorControllerFeatures
  Includes all the methods the controller needs for changing the animation while it is running.
  Signals to the view tht an action needs to be taken.

New Class Descriptions:
 AlgoInsertSortAnimation
  Algorithmic runnable (main) method that creates a textual description of an animation showing insert
  sort on a variable number of elements (defined in constants in the file). Text is written to file
  insert-sort-animation.txt inside the resources folder.
 InteractiveController
  Rename of the Simple Controller from the previous assignment. Adds features that allow for interactivity
  with input from the user while the animation is running.
 InteractiveAnimatorView
  Implementation of Simple Animator View. Plays the animation and creates buttons to allow for user
  interaction.

### ASSIGNMENT 5 DESCRIPTION / FILE LIST:
Changelog from assignment 4:
- changed View interface name and implementation name to fit the new interface and implementations
- Changed model to not take in an animation duration
  instead it now dynamically grows the duration depending on the end time of added shapes
- changed the model functionality to throw exception if you try to add a shape that's already in the animation
- changed the signature of the removeshape method to just take the shapeID, no longer takes an endTime
- Changed Position2D to be of of type float and can now be negative
- Changed the size list to just be two floats for width and height,
  also removed enum for square and circle, those can be made with rectangle and oval now
- refactored the getShapeCommandsAsText to provide more info and take in a conversion number.
- changed move animations to allow shapes to move to/from positions outside the animation bounds
- Added ComplexAnimatorView, which extends TextualAnimatorView; describes more functionality for all views.
  Previous classes now extend an abstract class that implements the new interface.
- Made implementation details for AnimatedShape and AniCommand public so the svg view can access to
  build it's view. Added tests for now public methods. Constructors still private.
- Modified TextualAnimatorView to take in ticks per second and convert its representation of the
  model to have time in seconds instead of ticks.
- Position2D can now be any position and the model supports shapes at any position
  (even outside the animation dimensions).

High-level Description:
  Model holds the entire animation. This includes shapes, animations applying to those shapes, and
  the bounds of the animation. Shapes and animations can be added and removed from the animation.

  The animation can be visualized in 3 ways: text-based, with SVG graphics, or with with build-in
  visual graphics from the Java Swing Library.

  A simple controller can control the visual animation
  by incrementing the current tick of the animation and passing it to the view which queries the
  model to get the shapes as they appear at that time.

  Using the provided class and model, there is now a way to convert a text based description of an
  animation into a SimpleModel which can then be visualized using the implementations created.

Interface Descriptions:
  Animator Controller:
    Represents a controller for an animation. Decides when things happen. Currently doesn't do much
    because there isn't much interaction between the user and an animation during runtime.
  TweenModelBuilder
    Represents an interface between local implementation and the AnimationFileReader provided as
    part of assignment 5. Allows the AnimationFileReader to create a model and fill it with shapes.
  AniCommand
    Represents an animation command that can be applied to a shape. Different implementations store
    different values but holds the methods that all commands should have in common. Meant mainly
    for the view otherwise would be package private.
  AnimatorModelState
    Describes all functions necessary to get info about the state of the model. Doesn't contain any
    mutating functions.
  AnimatorModel
    Describes all the other functions for a model that involve mutation. Extends AnimatorModelState.
  AniShape
    Represents an animated shape. Holds only getter methods as the interface is meant to provide
    access to the SVG view to build its representation of the animation.
  TextualAnimatorView
    Represents a view that only has a toString method. Not used in implementation but can be used to
    handle views where that is the only method desired. (SVG and text views throw
    UnsupportedOperationException if any other method called).
  ComplexAnimatorView
    Represents a more detailed interface for a view of the animation. Currently has makeVisible,
    refresh, and toString as it's methods. Implementations can choose which methods to implement and
    can throw UnsupportedOperationException for the other ones.

Enumeration Descriptions:
  ShapeType:
    Enumerates the different shapes supported by the animation. Also has info regarding the number
    of size integers necessary to describe that shape.


Class Descriptions:
  AbstractTextAnimatorView:
    Implements ComplexAnimatorView interface. Contains code that is the same for SVGAnimatorView
    and TextAnimatorView. Implemented methods are not included in sub classes' functionality.
  AnimatorGraphicsView:
    Implementation of complex animator view. Creates the drawing panel and queries
    the view to draw an animation.
  AnimatorPanel:
    Extends JPanel and implements methods for drawing an animation at a time.![](
  AnimatorViewCreator:
    Factory builder for animation view implementations.
  SVGAnimatorView:
    Extends AbstractTextAnimatorView, represents the animation in an svg file format.
  TextAnimatorView:
    Extends AbstractTextAnimatorView, represents the animation in a readable text output.
  SimpleAnimationController:
    Implementation of AnimatorController, plays an animation
  AnimationFileReader:
    A file reader for the animation file, reads in the file in the prescribed file format,
    create a model that implements the model builder interface.
  TweenModelBuilderImpl:
    Implementation of TweenModelBuilder, a builder class used for creating an animator model.
  AnimatorTextView:
    Implementation of AnimatorView interface. Overrides toString method to give a textual
    description of the shapes in the animation
  SimpleModel:
    Implementation of AnimatorModel (and AnimatorModelState), stores the info and state of the
    entire animation.
  Position2D:
    A value class representing a position in the animation (x-y coords).
  Shape:
    A value class representing a shape at a moment in time. Contains all the info necessary for a
    view to render it.
  AnimatedShape:
    Class to store a shape and all animations that act upon that shape. Has methods necessary to
    get the shape at a specific time in the animation.
  AbstractAniCommand:
    Extends AniCommand and has abstracted code that is the same for all animated commands, mainly
    time fields.
  MoveAniCommand:
    Extends AbstractAniCommand, represents a shape moving from 1 position to another during a
    specified time range.
  ColorAniCommand:
    Extends AbstractAniCommand, represents a shape changing color during a specified time range.
  ScaleAniCommand:
    Extends AbstractAniCommand, represents a shape changing size (changing dimensions) during a
    specified time range.



### ASSIGNMENT 4 DESCRIPTION / FILE LIST:
High-level Description:

The model stores shapes and animations applied to them. Shapes and animations can be added to and
removed from the model. Shapes are identified in the model via unique string ids. You can ask the
model for the shapes at a particular 'tick' or a single shape at a single tick.
You can get a list of the shapeIDs in the animation, a textual description of a specific shape
for the entire animation, the duration of the animation, the width of the animation,
the height of the animation, and what shapes can be added to the
animation (along with their size parameters).

Shapes in the model are represented by animated shapes, which store all animations applied to that
specific shape in several lists. Currently there are 3 lists, 1 for each of moveAnimations,
colorAnimations, and scaleAniamtions.

Animations are added to shapes using animation commands. The three types of animation commands are
move, scale, and color.
Move animations move a shape from 1 position2D to another position2D.
Color animations change the color of a shape.
Scale animations change the size/dimensions of a shape.

A specific shape at a moment in time can be represented via the Shape class. This is a value class
and has all the properties of a shape necessary to draw it. A Shape has an ID, an enumerated
type (square, rectangle, oval, circle), a Position2D (x-y coords),
a color (built-in java Color), and a list of Integer of sufficient size to define the
shape (1 for square, 2 for rect. etc.)

Interface Descriptions:
  AnimatorView:
    describes the view for an animation. Only has a toString method at the moment.
  AnimatorModelState:
    Describes all functions necessary to get info about the state of the model. Doesn't contain any
    mutating functions.
  AnimatorModel:
    Describes all the other functions for a model that involve mutation. Extends AnimatorModelState.
  AniCommand:
    Interface describing all the necessary functions needed for an Animated command that applies to
    a shape during the animation.

Enumeration Descriptions:
  ShapeType:
    Enumerates the different shapes supported by the animation. Also has info regarding the number
    of size integers necessary to describe that shape.

Class Descriptions:
  AnimatorTextView:
    Implementation of AnimatorView interface. Overrides toString method to give a textual
    description of the shapes in the animation
  SimpleModel:
    Implementation of AnimatorModel (and AnimatorModelState), stores the info and state of the
    entire animation.
  Position2D:
    A value class representing a position in the animation (x-y coords).
  Shape:
    A value class representing a shape at a moment in time. Contains all the info necessary for a
    view to render it.
  AnimatedShape:
    Class to store a shape and all animations that act upon that shape. Has methods necessary to
    get the shape at a specific time in the animation.
  AbstractAniCommand:
    Extends AniCommand and has abstracted code that is the same for all animated commands, mainly
    time fields.
  MoveAniCommand:
    Extends AbstractAniCommand, represents a shape moving from 1 position to another during a
    specified time range.
  ColorAniCommand:
    Extends AbstractAniCommand, represents a shape changing color during a specified time range.
  ScaleAniCommand:
    Extends AbstractAniCommand, represents a shape changing size (changing dimensions) during a
    specified time range.

