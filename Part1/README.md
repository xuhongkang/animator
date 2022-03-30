# OOD Assignment 4: Animator Model
Team 75993: Hongkang Xu and Shengjun Sun

## Overview
In this assignment, we used various design patterns introduced in class to implement a mvc (model-view-controller) java project that could:

1. Take Designated Inputs via the Controller in a batch (one-time)
2. Store Particular Shapes and Corresponding Motions via the Model
3. Visualize the Shapes and Motions via the View

## Documentation

Interacting with the controller:
The controller takes in a text file with uniquely formatted commands. 

These commands are listed below:
_____
### create/

tag/{BasicShape shape},{String tag}
_____

2 Arguments: Takes a BasicShape and String, Creates a new Shape with these parameters
_____
### tag/

tag/{BasicShape shape = null}{String tag = null}

tag/{BasicShape shape = null}{String tag}

tag/{BasicShape shape},{String tag}
_____

No Arguments: Assumes Both Parameters For the Current Motion is Null*

1 Argument: Assumes given shape is null, or unidentified, Takes and Assigns String tag**

2 Arguments: Takes a BasicShape and String, Assigns Both Parameters**
____
### time/

time/{Integer startTime = null}{Integer endTime = null}

time/{Integer startTime = null}{Integer endTime}

time/{Integer startTime},{Integer endTime}
_____

No Arguments: Assumes Both Parameters For the Current Motion is Null

1 Argument: Assumes startTime is null, or unidentified, Takes and Assigns Integer endTime

2 Arguments: Takes two Integers for start and end time, Assigns Both Parameters
_____
### sloc/

sloc/{Integer startXLocation = null}{Integer startYLocation = null}

sloc/{Integer startXLocation},{Integer startYLocation}
_____

No Arguments: Assumes Both Parameters For the Current Motion is Null

2 Arguments: Takes two Integers for start location X and Y, Assigns Both Parameters
_____
### eloc/

eloc/{Integer endXLocation = null}{Integer endYLocation = null}

eloc/{Integer endXLocation},{Integer endYLocation}
_____

No Arguments: Assumes Both Parameters For the Current Motion is Null

2 Arguments: Takes two Integers for end location X and Y, Assigns Both Parameters
_____
### sdim/

sdim/{Integer startWidth = null}{Integer startHeight = null}

sdim/{Integer startWidth},{Integer startHeight}
_____
No Arguments: Assumes Both Parameters For the Current Motion is Null

2 Arguments: Takes two Integers for start dimensions Width and Height, Assigns Both Parameters
_____
### edim/

edim/{Integer endWidth = null},{Integer endHeight = null}

edim/{Integer endWidth},{Integer endHeight}
_____
No Arguments: Assumes Both Parameters For the Current Motion is Null

2 Arguments: Takes two Integers for end dimensions Width and Height, Assigns Both Parameters
_____
### color/

color/{BasicColor startColor = null}{BasicColor endColor = null}

color/{BasicColor startColor = null}{BasicColor endColor}

color/{BasicColor startColor},{BasicColor endColor}
_____

No Arguments: Assumes Both Parameters For the Current Motion is Null

1 Argument: Assumes BasicColor startColor is null, or unidentified, Takes and Assigns BasicColor endColor

2 Arguments: Takes two BasicColors for start and end color, Assigns Both Parameters
_____
### Shortcuts

The shortcut feature aims to cut down as many unnecessary commands as possible to enhance user experience.

Whenever a start parameter is undefined (or null), the model will search for the previously defined such parameter (the end parameter from the last available state) and extend its value.

Whenever an end parameter is undefined (or null), the model will search for the previously defined such parameter (the start parameter in this motion) and extend its value.

Both of this features can be simultaneously implemented: Meaning that if a command isn't written or its parameters are empty, the start parameter will attempt to copy its most previous counterpart (if available), and the end parameter will copy that value as well. So if there aren't any changes, the command can be omitted. 

It is recommended that you check for a shape whether it has been initialized to some state before using the shortcut feature.
______

### Line Formatting:

Commands for the same motion are called on the same line, separated by spaces (of any length). Each set of commands on a new line mean that a new motion is initialized. Commands do not need to be in a specific order.

Shapes must be initialized (via create/) before a motion can be added. It is recommended that create/ commands are called separately on new lines.

\* Tags help define which shape a motion belongs to. If its parameters are not set, it will assume that the current motion is implemented upon the last created shape. 
  
**
  If the tag is present in the command but the shape is not, the model will not bother to check its validity. Likewise, if the shape is present, the model will check if the tag does correspond to the provided shape, thus with additional information the commands will be implemented more accurately.
_____

### Examples:

    create/R,rect
    tag/R time/1,12 sloc/200,200 eloc/50,100 sDim/50,50 color/blue
    time/12,15 eDim/20,20
