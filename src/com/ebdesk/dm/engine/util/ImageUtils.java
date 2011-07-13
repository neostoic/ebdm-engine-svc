/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ebdesk.dm.engine.util;

import java.awt.Dimension;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 *
 * @author Muhammad Rifai
 */
public class ImageUtils {

    public static Dimension imageDimension(String imagePath) {
        InputStream is = null;
        ImageInputStream in = null;
        try {
            is = new FileInputStream(imagePath);
            in = ImageIO.createImageInputStream(is);
            final Iterator readers = ImageIO.getImageReaders(in);
            if (readers.hasNext()) {
                ImageReader reader = (ImageReader) readers.next();
                try {
                    reader.setInput(in);
                    return new Dimension(reader.getWidth(0), reader.getHeight(0));
                } finally {
                    reader.dispose();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ImageUtils.class.getName()).log(Level.SEVERE, null, ex);
            return new Dimension(-1, -1);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    Logger.getLogger(ImageUtils.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                    Logger.getLogger(ImageUtils.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return new Dimension(-1, -1);
    }
}
