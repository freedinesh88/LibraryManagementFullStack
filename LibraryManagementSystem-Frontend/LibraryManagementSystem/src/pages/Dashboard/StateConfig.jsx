import React from 'react'
import EventAvailableIcon from '@mui/icons-material/EventAvailable'
import HistoryIcon from '@mui/icons-material/History'
import TrendingUpIcon from '@mui/icons-material/TrendingUp'
import {LibraryBooks} from '@mui/icons-material'

export const stateConfig = ({myLoans,reservations,stats}) => [
    {
        id: "loans",
        title: "Current Loans",
        subTitle: "Books youa are reading ",
        value: myLoans?.length,
        icon: <LibraryBooks  sx={{ fontSize: 32,color:"#4f46e5" }}/>,
        bgColor: "bg-indigo-100",
        textColor: "text-indigo-600"
    },
    {
        id: "reservations",
        title: "Reservations",
        subTitle: "Books on hold",
        value: reservations?.length,
        icon: <EventAvailableIcon  sx={{ fontSize: 32,color:"#4f46e5" }}/>,
        bgColor: "bg-purple-100",
        textColor: "text-purple-600"
    },
    {
        id: "read",
        title: "Books Read",
        subTitle: "This year ",
        value: myLoans?.length ?? 0,
        icon: <HistoryIcon  sx={{ fontSize: 32,color:"#108981" }}/>,
        bgColor: "bg-green-100",
        textColor: "text-green-600"
    },
    {
        id: "streak",
        title: "Day Streak",
        subTitle: "Keeps it going",
        value: stats?.readingStreak ?? 0,
        icon: <TrendingUpIcon sx={{ fontSize: 32,color:"#f59e08" }}/>,
        bgColor: "bg-orange-100",
        textColor: "text-orange-600"
    }
]