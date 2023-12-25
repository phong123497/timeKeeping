import axios from '../../services/axios';
import { 
    ActionType
} from '../actionTypes/companyActionTypes';


export const getCompanies = () => async (dispatch) => { 
    dispatch({  
        type: ActionType.FETCH_COMPANIES 
    });
    try {
        const response = await axios.get(`https://jsonplaceholder.typicode.com/comments`);
        dispatch({
            type: ActionType.FETCH_COMPANIES_SUCCESS,
            payload: response.data  
        });
    } catch(err) {
        dispatch({
            type: ActionType.FETCH_COMPANIES_FAIL,
            payload: err
        });
    }
}
export const getCompany = (id) => async (dispatch) => { 
    dispatch({
        type: ActionType.FETCH_COMPANY
    });

    try {
        const response= await axios.get(`https://jsonplaceholder.typicode.com/comments/${id}`);
        dispatch({
            type: ActionType.FETCH_COMPANY_SUCCESS,
            payload: response.data  
        });

    } catch(err) {
        dispatch({
            type: ActionType.FETCH_COMPANY_FAIL,
            payload: err
        });
    }
}
export const addCompany = (company)=> async (dispatch) => { 
    dispatch({ 
        type: ActionType.ADD_COMPANY
    });
    try {
        const response = await axios.post(`/companies`, company);
        dispatch({
            type: ActionType.ADD_COMPANY_SUCCESS,
            payload: response.data  
        });

    } catch(err) {
        dispatch({
            type: ActionType.ADD_COMPANY_FAIL,
            payload: err
        });
    }
};
export const editCompany = (editedCompany) => async (dispatch) => { 
    dispatch({ type: ActionType.EDIT_COMPANY });
    try {
        const response = await axios.patch(`/companies/${editedCompany.id}`, editedCompany);
        dispatch({ 
            type: ActionType.EDIT_COMPANY_SUCCESS, 
            payload: response.data
        });
    } catch (err) {
        dispatch({ 
            type: ActionType.EDIT_COMPANY_FAIL,
            payload: err
        });
    }
};


