import { CircularActionType } from '../actionTypes/circularActionTypes';

const initialState = {
    loading: false,
    error: null,
};
export const CircularReducer = (
    state = initialState,
    action
) => {
    switch (action.type) {
        case CircularActionType.OPEN_CIRCULAR:
            return { ...state, loading: true };
        case CircularActionType.CLOSE_CIRCULAR:
            return { ...state, loading: false, error: null };
        case CircularActionType.CLOSE_AND_ERROR_CIRCULAR:
            return { ...state, loading: false, error: action.payload };
        default:
            return state;
    }
};
