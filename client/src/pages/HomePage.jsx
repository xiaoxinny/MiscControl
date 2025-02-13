import React, { useEffect, useState } from "react";
import { Box, Typography, Button, Divider, Chip } from "@mui/material";
import { useNavigate } from "react-router-dom";
import "../styles/HomePage.css";

function HomePage() {
    const navigate = useNavigate();
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    useEffect(() => {
        const token = localStorage.getItem("token");
        if (token) {
            setIsLoggedIn(true);
        }
    }, []);

    return (
        <Box
            sx={{
                backgroundColor: 'rgba(240, 240, 240, 0.95)',
                py: 4,
                px: 4,
                borderRadius: '20px',
                margin: 'auto',
                boxShadow: 1,
            }}
        >
            {isLoggedIn ? (
                <Typography variant="title" color="black" class="limelight-regular">
                    Welcome back to Bookworms Online!
                </Typography>
            ) : (
                <>
                    <Typography variant="title" color="black" class="limelight-regular">
                        Let your creativity run wild
                    </Typography>
                    <Divider sx={{mt: 2, mb: 4}}>
                        <Chip label="FREEDOM" size="small" />
                    </Divider>
                    <Box sx={{ display: 'flex', justifyContent: 'center'}}>
                        <Typography variant="title" color="black">
                            All books you ever need at Bookworms Online.
                        </Typography>
                    </Box>
                    <Box sx={{ display: 'flex', justifyContent: 'center' }}>
                        <Button
                            variant="contained"
                            sx={{
                                backgroundColor: "black",
                                color: "white",
                                "&:hover": {
                                    backgroundColor: "darkgrey",
                                },
                                borderRadius: "9999999px",
                                mt: 4
                            }}
                            onClick={() => { navigate('/login') }}
                        >
                            Enter the world of imagination
                        </Button>
                    </Box>
                    <Box sx={{ display: 'flex', justifyContent: 'center' }}>
                        <Typography variant="title" sx={{ mt: 2, fontSize: 14 }}>Don't have an account?
                            <Button onClick={() => navigate('/registration')} color="primary">
                                Register here.
                            </Button>
                        </Typography>
                    </Box>
                </>
            )}
        </Box>
    );
}

export default HomePage;