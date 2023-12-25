import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import './index.css'
import './i18n.js'
import { BrowserRouter } from 'react-router-dom'
import { Provider } from 'react-redux';
import { store } from './redux/store/store'
import { ContextProvider } from './context';

ReactDOM.createRoot(document.getElementById('root')).render(

<>
      {/* <React.StrictMode> */}
        <Provider store={store}>
          <ContextProvider>
            <BrowserRouter>
              <App />
            </BrowserRouter>
          </ContextProvider>
        </Provider>
      {/* </React.StrictMode>, */}
</>
) 
