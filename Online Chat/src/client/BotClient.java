package client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BotClient extends Client {
    public class BotSocketThread extends SocketThread {
        @Override
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            BotClient.this.sendTextMessage("Привет чатику. Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды");
            super.clientMainLoop();
        }

        @Override
        protected void processIncomingMessage(String message) {
            super.processIncomingMessage(message);
            String[] arr = message.split(": ");
            if (arr.length != 2) return;
            String text = arr[1];
            String name = arr[0];
            Date date = Calendar.getInstance().getTime();
            String answer = "Информация для " + name + ": ";
            if (text.equalsIgnoreCase("дата")) answer += new SimpleDateFormat("d.MM.yyyy").format(date);
            else if (text.equals("день")) answer += new SimpleDateFormat("d").format(date);
            else if (text.equals("месяц")) answer += new SimpleDateFormat("MMMM").format(date);
            else if (text.equals("год")) answer += new SimpleDateFormat("yyyy").format(date);
            else if (text.equals("время")) answer += new SimpleDateFormat("H:mm:ss").format(date);
            else if (text.equals("час")) answer += new SimpleDateFormat("H").format(date);
            else if (text.equals("минуты")) answer += new SimpleDateFormat("m").format(date);
            else if (text.equals("секунды")) answer += new SimpleDateFormat("s").format(date);
            else return;
            BotClient.this.sendTextMessage(answer);
        }
    }

    @Override
    protected BotSocketThread getSocketThread() {
        return new BotSocketThread();
    }

    @Override
    protected boolean shouldSendTextFromConsole() {
        return false;
    }

    @Override
    protected String getUserName() {
        return "date_bot_" + (int) (Math.random() * 100);
    }

    public static void main(String[] args) {
        BotClient botClient = new BotClient();
        botClient.run();
    }
}
