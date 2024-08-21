import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

@WebServlet({"/move"})
public class ServerMove extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // @Override
    // protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    //     BufferedWriter out = new BufferedWriter(new OutputStreamWriter(resp.getOutputStream(), "UTF-8"));
    //     resp.setContentType("text/html");
    //     resp.setStatus(200);
    //     out.write("Hi!");
    //     out.flush();
    // }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject moveData = new JSONObject(req.getReader().readLine());
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(resp.getOutputStream(), "UTF-8"));
        resp.setContentType("application/json");
        resp.setStatus(200);
    
        String prevBoard = moveData.getString("board");
        int move = moveData.getInt("move");  
        TicTacToe game = new TicTacToe();
        game.drawBoard(prevBoard);
        JSONObject response = new JSONObject();
        if (game.checkValidMove(move)) {
            game.updateClientMove(move);
            if(game.checkWinner("XXX")) {
                response.put("status", "You won!");
            } else {
                if (game.isBoardFull()) {
                    response.put("status", "Draw!");
                } else {
                    game.makeServerMove();
                    if(game.checkWinner("OOO")) {
                        response.put("status", "You lost!");
                    } else if (game.isBoardFull()) {
                        response.put("status", "Draw!");
                    } else {
                        response.put("status", "Continues!");
                    }
                }
            }
        } else {
            response.put("status", "Invalid Move!");
        }
        response.put("board", game.getCurrentBoard());
        out.write(response.toString());
        out.flush();
    }

}