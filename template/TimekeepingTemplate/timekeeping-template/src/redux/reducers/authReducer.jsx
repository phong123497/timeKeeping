import { ActionType } from '../actionTypes/authActionTypes';

const initialState = {
    authToken: null,
    isPasswordChanged: false,
    user: null,
    error: null,
    isSendPass:null
}

export const AuthReducer = (
    state = initialState,
    action
) => {
    switch (action.type) {
        case ActionType.AUTH_START:
            return { ...state, loading: true };
        case ActionType.AUTH_SUCCESS:
            sessionStorage.setItem("TOKEN_AUTH", action.payload.body.token)
            return {
                ...state,
                authToken: action.payload.body.token,
                isPasswordChanged: false,
                user: action.payload.body.user,
                error: null,
            };
        case ActionType.AUTH_FAIL:
            return {
                ...state,
                authToken: null,
                isPasswordChanged: false,
                user: null,
                error: action.payload,
            };
        case ActionType.PASSWORD_SUCCESS:
            return{
                ...state,
                isSendPass:action.payload.body
            }
        case ActionType.PASSWORD_FAIL:
            return{
                ...state,
                isSendPass:action.payload.body
            }
        default:
            return state;
    }
};