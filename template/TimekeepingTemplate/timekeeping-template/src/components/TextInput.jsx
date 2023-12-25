import React, { useState } from "react";
import TextField from "@mui/material/TextField";
import { TEXTINPUT_TYPE } from "../static/js/constant";
import { useTranslation } from 'react-i18next';

import {
  validateHiragana,
  validateKatagana,
  validateEmail,
  validateHalfAlphaNum,
  validateNumber,
  validatePassword
} from "../static/js/common.js";
function TextInput({
...props}) {
  const [showValid, setShowValid] = useState("");
  const [isError, setIsError] = useState(false);
  const { t, i18n } = useTranslation();
  const handleChange = (e) => {
    // validate（）を呼び出す
    validate(e.target.value, props.checkType);

    if (props.onChange) {
      props.onChange(e);
    }
  };
  const validate = (value, checkType,) => {
    // Reset state
    setShowValid("");
    setIsError(false);

    // check type text
    switch (checkType) {
      // // 全角ひらがなのみ
      case TEXTINPUT_TYPE.HIRAGANA:
        if(validateHiragana(value)){
          setShowValid(t('PleaseEnterInHiragana!'));
          setIsError(true);
        }
        break;
      case TEXTINPUT_TYPE.HALF_KATAKANA:
        if(validateKatagana(value)){
           setShowValid(t('PleaseEnterInKatagana!'));
          setIsError(true);

        }
        break;
      case TEXTINPUT_TYPE.HALF_ALPHA_NUM:
        if(validateHalfAlphaNum(value)){
           setShowValid(t('PleaseEnterInHalf-widthAlphanumericCharacters!'));
          setIsError(true);
        }
        break;
      // check password
      case TEXTINPUT_TYPE.PASSWORD:
        if(validatePassword(value)){
           setShowValid(t('mistakeFormatMyPassword!'));
          setIsError(true);

        }
        break;
      //check email validate
      case TEXTINPUT_TYPE.EMAIL:
        if(validateEmail(value)){
           setShowValid(t('mistakeInTheEmailFormat!'));
          setIsError(true);

        }
        break;
      case TEXTINPUT_TYPE.NUMBER:
        if(validateNumber(value)){
           setShowValid(t('PleaseEnterNumbers!'));
          setIsError(true);

        }
        break;
      default:
        break;
    }
  };
  return (
    <div>
      <TextField
        value={props.value}
        required={props.require}
        error={isError}
        type={props.type}
        helperText={showValid}
        placeholder={props.placeholder}
        onChange={handleChange}
        sx={{
          "& .MuiInputBase-input": {
            padding: 0
          },
          "& .MuiOutlinedInput-root": {
            fontSize: "13px",
            height: props.height || "40px",
            width: props.width || "400px",
            border: props.border || "1px solid #F0F0F0",
            padding: "12px 0px 12px 8px",
            "& > fieldset": {
              top: "0px",
              padding: "0",
              border: "none"
            }
          },
          "& .MuiOutlinedInput-root.Mui-focused": {
            border: "1px solid #303E65"
          },
          "& .MuiOutlinedInput-input": {
            fontSize: "13px",
            fontWeight: "400",
            lineHeight: "normal",
            borderRadius: "4px",
            color: "303E65"
          },
          "& .MuiFormHelperText-root.Mui-error": {
            margin: "0",
          },
          marginBottom: props.helperText ? "-16px" : "0px"
        }}
      />
    </div>
  );
}

export default TextInput;
