package common;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/push-hello"}, asyncSupported = true)
public class PushHello extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            System.out.println("SSE Demo");
            response.setContentType("text/event-stream");

            PrintWriter pw = response.getWriter();
            int i = 0;
            while (true) {
                Thread.sleep(2000);
                i++;
                pw.write("event: message\n");  //take note of the 2 \n 's, also on the next line.
                pw.write("data: " + i + "\n\n");
                System.out.println("Data Sent!!!" + i);
                pw.flush();
                response.flushBuffer();
                if (i > 10)
                    break;
            }
            pw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        doPost(request, response);
    }
}