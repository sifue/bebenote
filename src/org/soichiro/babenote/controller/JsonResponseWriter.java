/**
 * 
 */
package org.soichiro.babenote.controller;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.arnx.jsonic.JSON;

import org.slim3.util.ThrowableUtil;

/**
 * JsonResponseWriter
 * @author soichiro
 *
 */
public class JsonResponseWriter {

	/**
	 * private constructor
	 */
	private JsonResponseWriter() {
		super();
	}
	
    /**
     * Write json to HttpServletResponse
     * @param data
     * @param request
     * @param response
     */
    public static void json(Object data, 
    		HttpServletRequest request,
    		HttpServletResponse response){
    	json(JSON.encode(data), request, response);
    }
    
    /**
     * Write json to HttpServletResponse
     * @param json
     * @param request
     * @param response
     */
    public static void json(String json, 
    		HttpServletRequest request,
    		HttpServletResponse response){
        String encoding = request.getCharacterEncoding();
        if (encoding == null) {
            encoding = "UTF-8";
        }
        response.setContentType("application/json; charset=" + encoding);
        try {
            PrintWriter out = null;
            try {
                out = new PrintWriter(new OutputStreamWriter(response
                        .getOutputStream(), encoding));
                out.print(json);
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        } catch (IOException e) {
            ThrowableUtil.wrapAndThrow(e);
        }
    }
	
}
