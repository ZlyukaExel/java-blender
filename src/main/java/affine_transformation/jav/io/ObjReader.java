package affine_transformation.jav.io;

import affine_transformation.jav.math.Vector2f;
import affine_transformation.jav.math.Vector3f;
import affine_transformation.jav.model.Model;
import affine_transformation.jav.model.Polygon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class ObjReader {

    public static Model read(String filename) throws IOException {
        Model model = new Model();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();

                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                try {
                    processLine(line, model, lineNumber);
                } catch (ObjReaderException e) {
                    System.err.println("Error at line " + lineNumber + ": " + e.getMessage());
                    throw e;
                }
            }
        }

        return model;
    }

    private static void processLine(String line, Model model, int lineNumber) throws ObjReaderException {
        String[] tokens = line.split("\\s+");
        if (tokens.length == 0) return;

        String keyword = tokens[0];
        String[] arguments = Arrays.copyOfRange(tokens, 1, tokens.length);

        switch (keyword) {
            case "v":
                parseVertex(arguments, model, lineNumber);
                break;
            case "vt":
                parseTextureVertex(arguments, model, lineNumber);
                break;
            case "vn":
                parseNormal(arguments, model, lineNumber);
                break;
            case "f":
                parseFace(arguments, model, lineNumber);
                break;
            default:
                break;
        }
    }

    private static void parseVertex(String[] args, Model model, int lineNumber) throws ObjReaderException {
        if (args.length < 3) {
            throw new ObjReaderException("Too few vertex coordinates");
        }

        try {
            float x = Float.parseFloat(args[0]);
            float y = Float.parseFloat(args[1]);
            float z = Float.parseFloat(args[2]);
            model.addVertex(new Vector3f(x, y, z));
        } catch (NumberFormatException e) {
            throw new ObjReaderException("Invalid vertex coordinates: " + Arrays.toString(args));
        }
    }

    private static void parseTextureVertex(String[] args, Model model, int lineNumber) throws ObjReaderException {
        if (args.length < 2) {
            throw new ObjReaderException("Too few texture vertex coordinates");
        }

        try {
            float u = Float.parseFloat(args[0]);
            float v = args.length >= 2 ? Float.parseFloat(args[1]) : 0.0f;
            model.addTextureVertex(new Vector2f(u, v));
        } catch (NumberFormatException e) {
            throw new ObjReaderException("Invalid texture vertex coordinates: " + Arrays.toString(args));
        }
    }

    private static void parseNormal(String[] args, Model model, int lineNumber) throws ObjReaderException {
        if (args.length < 3) {
            throw new ObjReaderException("Too few normal coordinates");
        }

        try {
            float x = Float.parseFloat(args[0]);
            float y = Float.parseFloat(args[1]);
            float z = Float.parseFloat(args[2]);
            model.addNormal(new Vector3f(x, y, z));
        } catch (NumberFormatException e) {
            throw new ObjReaderException("Invalid normal coordinates: " + Arrays.toString(args));
        }
    }

    private static void parseFace(String[] args, Model model, int lineNumber) throws ObjReaderException {
        if (args.length < 3) {
            throw new ObjReaderException("Face has too few vertices: " + args.length);
        }

        Polygon polygon = new Polygon();

        for (String arg : args) {
            String[] indices = arg.split("/");

            if (indices.length == 0) {
                throw new ObjReaderException("Invalid face element: " + arg);
            }

            try {
                int vertexIndex = Integer.parseInt(indices[0]) - 1;
                if (vertexIndex < 0 || vertexIndex >= model.getVertices().size()) {
                    throw new ObjReaderException("Invalid vertex index: " + (vertexIndex + 1));
                }
                polygon.addVertex(vertexIndex);

                if (indices.length > 1 && !indices[1].isEmpty()) {
                    int textureIndex = Integer.parseInt(indices[1]) - 1;
                    if (textureIndex < 0 || textureIndex >= model.getTextureVertices().size()) {
                        throw new ObjReaderException("Invalid texture vertex index: " + (textureIndex + 1));
                    }
                    polygon.addTextureVertex(textureIndex);
                }

                if (indices.length > 2 && !indices[2].isEmpty()) {
                    int normalIndex = Integer.parseInt(indices[2]) - 1;
                    if (normalIndex < 0 || normalIndex >= model.getNormals().size()) {
                        throw new ObjReaderException("Invalid normal index: " + (normalIndex + 1));
                    }
                    polygon.addNormal(normalIndex);
                }

            } catch (NumberFormatException e) {
                throw new ObjReaderException("Invalid face index format: " + arg);
            }
        }

        model.addPolygon(polygon);
    }

    public static class ObjReaderException extends IOException {
        public ObjReaderException(String message) {
            super(message);
        }
    }
}