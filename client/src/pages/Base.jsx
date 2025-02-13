import React, { useEffect, useState } from "react";
import { Outlet } from "react-router-dom";
import FacebookIcon from "@mui/icons-material/Facebook";
import TwitterIcon from "@mui/icons-material/Twitter";
import InstagramIcon from "@mui/icons-material/Instagram";
import LinkedInIcon from "@mui/icons-material/LinkedIn";
import { useNavigate } from "react-router-dom";
import { Box, Link, Typography, IconButton, SvgIcon, AppBar, Toolbar, Button, Container } from "@mui/material";

function Base() {
    const navigate = useNavigate();
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    useEffect(() => {
        const token = localStorage.getItem("token");
        if (token) {
            setIsLoggedIn(true);
        }
    }, []);

    return (
        <Box display="flex" flexDirection="column" minHeight="100vh">
            <Box sx={{ height: '4px', backgroundColor: 'green' }} />
            <AppBar position="static" sx={{ backgroundColor: "white" }}>
                <Toolbar sx={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', width: '100%' }}>
                    <Typography variant="title" color="black" sx={{ fontWeight: 'bold' }}>
                        <Link href="/home" underline="none" color="black">
                            Bookworms Online
                        </Link>
                    </Typography>
                    <Typography variant="title" color="black" sx={{ fontWeight: 'regular' }}>
                        Welcome to the online store!
                    </Typography>
                    {!isLoggedIn && (
                        <Box>
                            <Button
                                variant="contained"
                                sx={{
                                    backgroundColor: "black",
                                    color: "white",
                                    "&:hover": {
                                        backgroundColor: "darkgrey",
                                    },
                                    borderRadius: "5px",
                                    mr: 2
                                }}
                                onClick={() => { navigate('/login') }}
                            >
                                <Typography variant="title" color="white" sx={{ fontWeight: 'medium' }}>
                                    Login
                                </Typography>
                            </Button>
                            <Button
                                variant="contained"
                                sx={{
                                    backgroundColor: "black",
                                    color: "white",
                                    "&:hover": {
                                        backgroundColor: "darkgrey",
                                    },
                                    borderRadius: "5px",
                                }}
                                onClick={() => { navigate('/registration') }}
                            >
                                <Typography variant="title" color="white" sx={{ fontWeight: 'medium' }}>
                                    Register
                                </Typography>
                            </Button>
                        </Box>
                    )}
                </Toolbar>
            </AppBar>
            <Box sx={{ flexGrow: 1 , mx: 2, my: 2, justifyContent: 'center', alignItems: 'center', display: 'flex'}}>
                <Outlet />
            </Box>
            <Box sx={{ py: 6, textAlign: "center", backgroundColor: "white" }}>
                <Box sx={{ mb: 3 }}>
                    <IconButton aria-label="Facebook" color="inherit">
                        <SvgIcon component={FacebookIcon} />
                    </IconButton>
                    <IconButton aria-label="Twitter" color="inherit">
                        <SvgIcon component={TwitterIcon} />
                    </IconButton>
                    <IconButton aria-label="Instagram" color="inherit">
                        <SvgIcon component={InstagramIcon} />
                    </IconButton>
                    <IconButton aria-label="LinkedIn" color="inherit">
                        <SvgIcon component={LinkedInIcon} />
                    </IconButton>
                </Box>
                <Typography variant="caption" color="textSecondary">
                    2025 BookwormsOnline. All rights reserved.
                </Typography>
            </Box>
        </Box>
    );
}

export default Base;