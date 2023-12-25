import { ActionType } from '../actionTypes/alertActionTypes';

const initialState = {
    isModalOpen: false,
    message: 'abc',
  };

export const AlertReducer = (
    state = initialState,
    action
) => {
    switch (action.type) {
        case ActionType.OPEN_ALERT_MODAL:
            return {
              ...state,
              message: action.message,
              isModalOpen: true
            };
          case ActionType.TOGGLE_ALERT_MODAL:
            return {
              ...state,
              isModalOpen: !state.isModalOpen
            };
          default:
            return state;
    }
};


