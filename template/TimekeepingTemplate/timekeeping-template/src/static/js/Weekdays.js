export const GetWeekDays =(today)=>{
    switch (today) {
      case 0:
        return "Chủ nhật";
      case 1:
        return "Thứ hai";
      case 2:
        return "Thứ ba";
      case 3:
        return "Thứ tư";
      case 4:
        return "Thứ năm";
      case 5:
        return "Thứ sáu";
      case 6:
        return "Thứ bảy";
      default:
        return "";
    }
}
export const GetWeekDaysShort =(today)=>{
  switch (today) {
    case 0:
      return "CN";
    case 1:
      return "T2";
    case 2:
      return "T3";
    case 3:
      return "T4";
    case 4:
      return "T5";
    case 5:
      return "T6";
    case 6:
      return "T7";
    default:
      return "";
  }
}