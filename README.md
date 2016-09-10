# Mandelbrot_Generator
An interactive Mandelbrot generator that allows the user to modify and explore the Mandelbrot set

## Basic Usage
- The user may click and drag to select an area on the Mandelbrot set. Upon releasing the mouse, the set will zoom in to the selected area
- The panel in the bottom right of the screen allows the user to control the number of iteratations and escape value used to compute the set. The escape value will affect the contour of the colors bands (this will not affect performance). The greater the number of iterations entered, the more precise the set will be at the cost of performace.
- This panel also allows the user to reset the set and return to a zoomed out position with the default settings
- Other features can be accessed from the tabs on the right of the application, and are explained below:

## Features
- Allows the user to zoom in and out of the set
- Allows the user to undo and redo their zoom actions
- Allows the user to change how the Mandelbrot is colored
  - A preset color may be selected
  - A random color may be genereated (and saved if desired)
  - A custom color may be created with various sliders (and saved if desired)
- Allows the user to translate their view around the Mandelbrot set
- Allows the user to modify the formula of the set, and change how it is rendered
  - The exponent in the formula z = z^exponent + c may be changed
  - **The greater the exponent the longer the set will take to render**
- Allows the user to save locations they find in the set, and return to them later
- Allows the user to save images of what is currently rendered as a .png file
  - The user may choose the size of the image (in pixels) and how many iterations are used to generate this image
  - **Larger image sizes and larger iteration values will increase the time it takes to render and save the image**

### Notes
- The application window is not resizeable

