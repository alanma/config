package com.payegis.caesar.appservice.flumelog4j;

import org.apache.commons.logging.Log;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.cloudera.flume.log4j.appender.FlumeLog4jAvroAppender;
import com.payegis.caesar.appservice.config.Config;
import com.payegis.caesar.common.util.Log4jFactory;

public class FlumeLogger
{
   private static Log logger = Log4jFactory.getLog("appservice", FlumeLogger.class);
   private static final String FLUME_HOST = "log4j.appender.flume.hostname";
   private static final String FLUME_PORT = "log4j.appender.flume.port";
   private static final String FLUME_RECONNECT_ATTEMPT = "log4j.appender.flume.reconnectAttempts";

   private static Logger flumeLogger = null;
   static Object lock = new Object();

   public static Logger getLogger()
   {
      if (flumeLogger == null)
      {
         synchronized (lock)
         {
            Config cfg = Config.getInstance();
            if (flumeLogger == null)
            {
               try
               {
                  FlumeLog4jAvroAppender fAppender = new FlumeLog4jAvroAppender();
                  fAppender.setName("avro");
                  fAppender.setHostname(cfg.getString(FLUME_HOST));
                  fAppender.setPort(cfg.getInt(FLUME_PORT));
                  fAppender.setReconnectAttempts(cfg
                                    .getInt(FLUME_RECONNECT_ATTEMPT));

                  //flumeLogger = Logger.getRootLogger();
                  flumeLogger = Logger.getLogger("undefined_flume_to_hdfs");
                  flumeLogger.removeAllAppenders();
                  flumeLogger.addAppender(fAppender);
                  flumeLogger.setLevel(Level.WARN);
               }
               catch (Exception ex)
               {
                  logger.error(ex.getLocalizedMessage());
                  ex.printStackTrace();
               }
            }
         }
      }

      return flumeLogger;
   }

}

