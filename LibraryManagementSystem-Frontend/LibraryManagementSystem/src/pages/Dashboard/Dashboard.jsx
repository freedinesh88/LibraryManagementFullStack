import React from 'react'
import StateCard from './StateCard'
import { stateConfig } from './StateConfig'
import { AutoAwesome } from '@mui/icons-material'
import { LinearProgress } from '@mui/material'
import Box from '@mui/material/Box';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import CurrentLoans from './CurrentLoans'
import Reservation from './Reservation'
import ReadingHistory from './ReadingHistory'
import Recommendation from './Recommendation'

const Dashboard = () => {
    const [activeTab, setActiveTab] = React.useState(0);
    const handleTabChange = (event, newValue) => {
        setActiveTab(newValue);
    }
    const stateData = stateConfig({
        myLoans: [{ id: 1 }, { id: 2 }, { id: 3 }],
        reservations: [{ id: 1 }, { id: 2 }],
        stats: { readingStreak: 5 }
    })
    return (
        <div className='min-h-screen bg-gradient-to-r from-indigo-50 via-white to bg-purple-500 py-8'>
            <div className='max-w-7xl px-4 sm:px-6 lg:px-8'>
                <div className='mb-8 animate-fade-in-up'>
                    <h1 className='text-4xl font-bold text-indigo-500 mb-2'>My {" "}
                        <span className='bg-gradient-to-r from-indigo-600 to-purple-600 bg-clip-text text-transparent'>Dashboard</span></h1>

                    <p className='text-lg text-gray-600'>Track your reading journey and manage your library</p>


                </div>

                {/* state cards */}

                <div className='grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6'>
                    {stateData.map((item) => (
                        <StateCard
                            bgColor={item.bgColor}
                            value={item.value}
                            title={item.title}
                            subTitle={item.subTitle}
                            textColor={item.textColor}
                            icon={item.icon}
                            key={item.id} />
                    ))}
                </div>

                {/* Reading progress section */}
                <div className='bg-white rounded-2xl shadow-2xl p-6 mb-8'>
                    <div className='flex items-center justify-between mb-4'>
                        <div>
                            <h3 className='text-xl font-bold text-gray-900 mb-1'>2025 Reading Goals</h3>
                        </div>
                        <p>
                            {250} of 30 books read
                        </p>
                    </div>
                    <div className='p-3 bg-gradient-to-r from-indigo-100 to-purple-100 rounded-full rounded-full'>
                        <AutoAwesome sx={{ fontSize: 32, color: "#4f46e5" }} />
                    </div>
                    <LinearProgress variant="determinate" value={30}
                        sx={{
                            height: 12,
                            borderRadius: 6,
                            backgroundColor: '#e0e7ff',
                            '& .MuiLinearProgress-bar': {
                                background: "linear-gradient(90deg, #4f46e5 0%, #9333ea 100%)",
                                borderRadius: 6,
                            },
                        }}
                    />

                    <p className='text-sm text-gray-600 mt-2'>70%</p>
                </div>

                {/* tab section */}
                <div className='bg-white rounded-2xl shadow-2xl overflow-hidden'>
                    <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                        <Tabs value={activeTab} onChange={handleTabChange} aria-label="basic tabs example">
                            <Tab label="Current Loans" />
                            <Tab label="Reservations" />
                            <Tab label="Reading History" />
                            <Tab label="Recommendations" />
                        </Tabs>

                    </Box>

                    {/* Current Loans content */}
                    {activeTab === 0 && <CurrentLoans />} 
                    {activeTab === 1 && <Reservation />} 
                    {activeTab === 2 && <ReadingHistory />} 
                    {activeTab === 3 && <Recommendation />} 

                </div>

            </div>
        </div>
    )
}

export default Dashboard
