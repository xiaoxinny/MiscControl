import { BrowserRouter as Router, Link, Routes, Route } from "react-router-dom";
import CssBaseline from '@mui/material/CssBaseline';
import Base from './pages/Base'
import Home from './pages/HomePage'
import Registration from './pages/Registration'
import Login from './pages/Login'

function App() {

    return (
        <Router>
            <CssBaseline />
            <Routes>
                <Route path="/" element={<Base />}>
                    <Route index path="/home" element={<Home />} />
                    <Route path="/registration" element={<Registration />} />
                    <Route path="/login" element={<Login />} />
                </Route >
            </Routes>
        </Router>
    )
}

export default App