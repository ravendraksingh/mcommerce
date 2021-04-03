package com.rks.userservice.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.hash.Hashing;
import com.rks.userservice.exception.BaseException;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

import static com.rks.userservice.constants.UserServiceConstants.FAILURE;
import static com.rks.userservice.constants.UserServiceErrorCodes.DATE_PARSE_ERROR;
import static java.util.Calendar.*;

public class CommonUtils {

  public static final SimpleDateFormat mysqlDateFormat = new SimpleDateFormat(
      "yyyy-MM-dd HH:mm:ss");
  /**
   * All possible chars for representing a number as a String
   */
  final static         char[]              digits          = {
      '0', '1', '2', '3', '4', '5',
      '6', '7', '8', '9', 'A', 'B',
      'C', 'D', 'E', 'F', 'G', 'H',
      'I', 'J', 'K', 'L', 'M', 'N',
      'O', 'P', 'Q', 'R', 'S', 'T',
      'U', 'V', 'W', 'X', 'Y', 'Z'
  };
  private static final Logger logger          = LogManager
      .getLogger(CommonUtils.class);
  private static final String              REQUEST_ID      = "requestId";
  //  static String             dateStr         = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  static Map<String, String> transactionType = new HashMap<>();
  static int count;
  private static ObjectMapper objectMapper = new ObjectMapper();

  //Regular expression to validate account number
  private static String  regex   = "\\d{12}";
  private static Pattern pattern = Pattern.compile(regex);

  static {
    transactionType.put("amtc", "A");
    transactionType.put("imps", "B");
    transactionType.put("neft", "C");
  }

  /*private CommonUtils() {
    throw new NotImplementedException(UTILS_INSTANTIATION);
  }*/

  /**
   * @return
   */
  public static String getUniqueTransactionId() {
    return UUID.randomUUID().toString();
  }

  public static String generatePayemtnId(int year) {
    Calendar cal = Calendar.getInstance();
    char date = digits[cal.getTime().getDate()];
    char month = digits[cal.getTime().getMonth()];
    char startCount = digits[15];
    String tt = transactionType.get("amtc");
    String hostname = "125";
    String countStr = String.format("%0$" + 5 + "s", toUnsignedString0(count++, 5))
        .replace(" ", "0");
    return tt + generateYearPart(year) + month + date + startCount + hostname + countStr;
  }

  private static String generateYearPart(int year) {
    year = year - 2000;
    int a = year % 36;
    int b = (year / 36) % 36;
    return "" + digits[b] + digits[a];
  }

  /**
   * Convert the integer to an unsigned number.
   */
  private static String toUnsignedString0(int val, int shift) {
    // assert shift > 0 && shift <=5 : "Illegal shift value";
    int mag = Integer.SIZE - Integer.numberOfLeadingZeros(val);
    int chars = Math.max(((mag + (shift - 1)) / shift), 1);
    char[] buf = new char[chars];

    formatUnsignedInt(val, shift, buf, 0, chars);

    // Use special constructor which takes over "buf".
    return new String(buf);
  }

  /**
   * Format a long (treated as unsigned) into a character buffer.
   *
   * @param val the unsigned int to format
   * @param shift the log2 of the base to format in (4 for hex, 3 for octal, 1 for binary)
   * @param buf the character buffer to write to
   * @param offset the offset in the destination buffer to start at
   * @param len the number of characters to write
   * @return the lowest character  location used
   */
  static int formatUnsignedInt(int val, int shift, char[] buf, int offset, int len) {
    int charPos = len;
    int radix = 1 << shift;
    int mask = radix - 1;
    do {
      buf[offset + --charPos] = digits[val & mask];
      val >>>= shift;
    } while (val != 0 && charPos > 0);

    return charPos;
  }

  public static String fileToString(File file) {
    StringBuilder builder = new StringBuilder();
    try {
      Files.lines(file.toPath()).forEach(builder::append);

    } catch (IOException e) {
      e.printStackTrace();
    }
    return builder.toString();
  }

  public static String datetimeToString(LocalDateTime dateTime, String format) {
    return dateTime.format(DateTimeFormatter.ofPattern(format));
  }

  public static String getFormattedDate(@NonNull String dateFormatter, @NonNull Date date) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatter);
    return simpleDateFormat.format(date);
  }

  /*
  public static String convertDateStringToEpoch(@NonNull String formatter, @NotNull String dateString) {
    try {
      SimpleDateFormat sdf = new SimpleDateFormat(formatter);
      Date d = sdf.parse(dateString);
      long epoch = d.getTime();
      logger.debug("Converted Date to epoch {}", String.valueOf(epoch));
      return String.valueOf(epoch);
    } catch (ParseException ex){
      logger.error("Invalid date format, exception {}",CommonsUtility.exceptionFormatter(ex));
      throw new BaseException(FAILED,INTERNAL_SERVER_ERROR,"Invalid date format");
    }
  }

  public static String convertDateStringToEpochSec(@NonNull String formatter, @NotNull String dateString) {
    try {
      SimpleDateFormat sdf = new SimpleDateFormat(formatter);
      Date d = sdf.parse(dateString);
      long epoch = d.getTime() / 1000L;
      logger.debug("Converted Date to epoch {}", String.valueOf(epoch));
      return String.valueOf(epoch);
    } catch (ParseException ex){
      logger.error("Invalid date format, exception {}",CommonsUtility.exceptionFormatter(ex));
      throw new BaseException(FAILED,INTERNAL_SERVER_ERROR,"Invalid date format");
    }
  }

  public static Long convertLocalDateTimeToEpoch(@NotNull LocalDateTime timestamp) {
    return timestamp.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
  }

  public static String dateFormatter(@NonNull String oldDateFormatter, @NonNull String newDateFormatter, @NotNull String dateString){
    try {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(oldDateFormatter);
    Date date = simpleDateFormat.parse(dateString);
    SimpleDateFormat newDateFormat = new SimpleDateFormat(newDateFormatter);
    String formattedDate = newDateFormat.format(date);
    return formattedDate;
    } catch (ParseException ex) {
      logger.error("Invalid date format, exception {} in converting from old format {} to new format {}",
              CommonsUtility.exceptionFormatter(ex), oldDateFormatter, newDateFormatter);
      throw new BaseException(FAILED,INTERNAL_SERVER_ERROR,"Invalid date format");
    }
  }


  //  To be used by all clients when unmarshalling responses from EIS
  public static <U extends BaseResponse> U unmarshallResponse(CustomResponse customResponse,
      Class<U> clazz,
      String serviceName) {
    if (Status.SUCCESS.equals(customResponse.getStatus())) {
      return objectMapper
          .convertValue(customResponse.getResponse(),
              clazz);
    } else {
      FailureResponse failureResponse = objectMapper.convertValue(customResponse.getResponse(),
          FailureResponse.class);
      throw getExceptionForCode(failureResponse.getErrorCode(), serviceName);
    }
  }

  public static String jsonObjectToString(Object object) throws JsonProcessingException {
    return objectMapper.writeValueAsString(object);
  }

  public static <U> U jsonStringToObject(String jsonString, Class<U> clazz) throws IOException {
    return objectMapper.readValue(jsonString, clazz);
  }

  //  To be used while handling all exceptions that come from external services
  public static BaseException getExceptionForCode(String errorCode, String serviceName)
      throws BaseException {
    try {
      BaseException exception = ServiceErrorFactory.getException(serviceName, errorCode)
          .orElse(ServiceErrorFactory
              .getException(TSERVICE, String.valueOf(NO_ERROR_CODE_FOUND_IN_DB)).get());
      logger.error(
          "Exception occurred in {} client. {}", serviceName, exception.toString());
      return exception;
    } catch (BaseException e) {
      logger.error("Internal server error in {} Client. {}", serviceName, e.toString());
      throw e;
    }
  }

  public static JsonNode convertStringToJson(String jsonString) {
    try {
      return objectMapper.readTree(jsonString);
    } catch (IOException e) {
      logger.error("Exception occurred while parsing json string: {}, exception: {}", jsonString,
          CommonsUtility.exceptionFormatter(e));
      throw new RuntimeException("Json parsing error");
    }
  }

  public static <T> T jsonToPojo(Object objectMap, Class<T> clazz) {
    return objectMapper.convertValue(objectMap, clazz);
  }

  public static <U> U convertJsonStringToMap(String jsonString, TypeReference typeRef) throws IOException {
    return objectMapper.readValue(jsonString, typeRef);
  }
  */

  /**
   * Converts a Map.toString() back to a Map
   * @param mapToString
   * @return Map<String, Object>
   */
  public static Map<String,Object> convertToStringToMap(String mapToString){
    Map<String,Object> data = new HashMap<String,Object>();
    Pattern p = Pattern.compile("[\\{\\}\\=\\, ]++");
    String[] split = p.split(mapToString);
    for ( int i=1; i+2 <= split.length; i+=2 ){
      data.put( split[i], split[i+1] );
    }
    return data;
  }

  public static boolean validateAccountNumber(String accountNumber) {
    if (StringUtils.isEmpty(accountNumber)) {
      return false;
    }
   /* Matcher matcher = pattern.matcher(accountNumber);
    return matcher.matches();*/
    return true;
  }



  public static String exceptionFormatter(Exception e) {
    StringBuilder sb = new StringBuilder(" | Exception : " + e.getClass());
    if (e.getCause() != null) {
      sb.append(" | Cause : " + e.getCause());
    }
    if (e.getMessage() != null) {
      sb.append(" | Exception message : " + e.getMessage().replaceAll("\"", ""));
    }
    if (e.getStackTrace() != null) {
      sb.append(" | StackTrace : " + StringUtils.join(
          Arrays.asList(e.getStackTrace()).subList(0, Math.min(e.getStackTrace().length, 15)),
          "____"));
    }
    return sb.toString();
  }

  public static int getDiffYears(Date first, Date last) {
    Calendar a = getCalendar(first);
    Calendar b = getCalendar(last);
    int diff = b.get(YEAR) - a.get(YEAR);
    if (a.get(MONTH) > b.get(MONTH) ||
        (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
      diff--;
    }
    return diff;
  }

  public static Calendar getCalendar(Date date) {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    return cal;
  }

  public static String getSchedulerRequestId(String prefix) {
    return
        "SCH" + (StringUtils.isNotEmpty(prefix) ? "-" + prefix
            : "") + "-" + LocalDateTime.now();
  }

  public static String convertDateToString(Date date, String format) {
    try {
      return new SimpleDateFormat(format).format(date);
    } catch (Exception e) {
      logger.error("Error in converting date:{} to string", date);
      return null;
    }
  }

  public static Date convertStringToDate(String date, String format) {
    try {
      return new SimpleDateFormat(format).parse(date);
    } catch (Exception e) {
      logger.error("Error in converting date:{} to string", date);
      return null;
    }
  }

  public static String formatDate(String inDate, String inputFormat, String outputFormat) {
    String outDate = "";
    if (inDate != null) {
      try {
        Date date = new SimpleDateFormat(inputFormat).parse(inDate);
        outDate = new SimpleDateFormat(outputFormat).format(date);
      } catch (Exception ex) {
        logger.error("Error in converting date:{} from inputFormat:{} to outputFormat", inDate,
            inputFormat, outputFormat);
        return null;
      }
    }
    return outDate;
  }

  public static Date formatDatesForLong(Long date, String inputFormat, Long duration) {
    Long inDate = date;
    try {
      SimpleDateFormat sdf = new SimpleDateFormat(inputFormat);
      if (Objects.nonNull(duration)) {
        inDate = date * duration;
      }
      return sdf.parse(sdf.format(new Date(inDate)));
    } catch (Exception e) {
      logger.error("Error in converting long:{} to date for inputFormat:{}", date, inputFormat);
      throw new BaseException(FAILURE, DATE_PARSE_ERROR,
          StringUtils
              .join("Invalid date format for date: ", date, " must be in format: ", inputFormat));
    }
  }

  public static Object getFieldUsingReflection(Object obj, String fieldName) {
    try {
      String methodName = "get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
      Method getterMethod = obj.getClass().getMethod(methodName, new Class[]{});
      Object data = getterMethod.invoke(obj);
      return data;
    } catch (Exception e) {
      logger.error("Error while calling getter using reflection {}", CommonUtils.exceptionFormatter(e));
      throw new BaseException("",12,"");
    }
  }

  public static Date convertToDate(String format, String date){
    try{
      return new SimpleDateFormat(format).parse(date);
    } catch (ParseException e) {
      logger.error("Error in parsing file");
      return null;
    }
  }

  public static List<String> getEnumNamesList(Class<? extends Enum<?>> e) {
    return Arrays.asList(getEnumNamesArray(e));
  }

  public static String[] getEnumNamesArray(Class<? extends Enum<?>> e) {
    String[] enumArray = Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    return enumArray;
  }

  public static String getDate() {
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    Date date = new Date();
    return formatter.format(date);
  }

  public static String createSHA256Hash(final String text) {
    String sha256hex = Hashing.sha256().hashString(text, StandardCharsets.UTF_8).toString();
    return sha256hex;
  }


}
