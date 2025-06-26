
import { Route, Routes } from 'react-router';

import "./App.css";
import MainLayout from './components/MainLayout';
import Home from './page/Home';
import Login from './page/Login'
import Register from './page/Register';
import Personal from './page/Personal'


function App() {
  return (
    <> 
      <Routes>
        <Route path="/" element={<MainLayout/>}>
          <Route index element={<Home />}/>
          <Route path="/personal" element={<Personal />}/>
          <Route 
            path="/login"
            element={
              <Login />
            }
          />
          <Route
            path='/register'
            element={<Register/>}
          />
        </Route>
      </Routes>
    </>
  );
}

export default App;
