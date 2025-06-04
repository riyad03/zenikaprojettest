import logo from './logo.svg';
import './App.css';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import MainApp from "./Main/general";
import Panier from "./Main/pages/Panier/Panier";
import Header from './Main/GeneralComponents/Headerjs';

// Création d'un objet pour exporter les fonctions de notification
export const notify = {
  success: (message) => toast.success(message),
  error: (message) => toast.error(message),
  info: (message) => toast.info(message),
  warning: (message) => toast.warning(message)
};

function App() {
  return (
    <Router>
      <div>
        <header>
          <Header></Header>
        </header>
        <Routes>
          <Route path="/" element={<MainApp />} />
          <Route path="/panier" element={<Panier />} />
        </Routes>
        <ToastContainer
          position="top-right"
          autoClose={3000}
          hideProgressBar={false}
          newestOnTop
          closeOnClick
          rtl={false}
          pauseOnFocusLoss
          draggable
          pauseOnHover
        />
      </div>
    </Router>
  );
}

export default App;
