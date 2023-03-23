# RayTracerV2

A simple Ray Tracer written in Kotlin.

## Included features
- shadows (tranparent materials also have shadows, unfortunately),
- recurring ray bounces,
- refraction,
- repositionable and adjustable camera,
- 3 different types of objects,
- 3 different types of materials,
- basic code paralellization,
- really simple GUI made with Compose Multiplaform framework.

## Usage

Ray Tracer can be run by running the Main.kt file.

Users can add objects by creating a .txt file and specifying objects' parameters in that file. Parameters are delimited with semicolons.

Format is as follows: 

```object_type;object's_parameters***;material_type;material's_parameters***```

***For example, if a Sphere object takes Point3d object (takes x, y and z coordinates as parameters) and a specific radius as parameters,
then Sphere object's parameters are passed as original object's parameters (same applies to material's parameters).
A red, diffuse (diffuse material only requires a color, which takes r, g and b values as parameters) sphere located at (0,0,0) in a three dimensional space with a radius of one half, would be added with a line looking like this:

```sphere;0.0,0.0,0.0;0.5;diffuse;1.0,0.0,0.0```

A few examples are already present in entities.txt file.

Every parameter has to be inputted as a float (e.g. 0.0, not 0).
