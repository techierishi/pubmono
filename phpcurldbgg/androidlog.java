  public enum COLOR {

        RED,
        GREEN,
        BLUE
    }

    public synchronized static void appendLog(ServletContext context, String text, boolean doAppend, COLOR color) {

        try {
            String path = context.getRealPath("/debug.html");
            File logFile = new File("" + path);

            String ecolor = "";
            if (color == COLOR.BLUE) {
                ecolor = "#2073F0";
            } else if (color == COLOR.GREEN) {
                ecolor = "#009A57";
            } else {
                ecolor = "#D33E2A";
            }

            if (!logFile.exists()) {
                try {
                    logFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                // BufferedWriter for performance, true to set append to file flag
                BufferedWriter buf = new BufferedWriter(new FileWriter(logFile,
                        doAppend));

                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String v_time = sdf.format(cal.getTime());

                buf.append(" <p style=\"color:" + ecolor + ";\">[" + v_time + "] " + text
                        + "</p>");

                buf.newLine();
                buf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
