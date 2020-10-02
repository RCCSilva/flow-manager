import React from 'react';
import BasicFlow from './components/BasicFlow';
import {
  BrowserRouter,
  Route,
  Link
} from "react-router-dom";

function App() {
  return (
    <div className='w-full h-full border'>
        <BrowserRouter>
          <nav className='px-16 py-4 shadow-2xl flex items-center'>
            <h1 className='font-bold text-lg'>Flow Manager</h1>
            <Link to="/create" className='ml-8 text-xl font-bold'>Create</Link>
          </nav>
          <Route path='/create' component={BasicFlow}/>
      </BrowserRouter>
    </div>
  );
}

export default App;
