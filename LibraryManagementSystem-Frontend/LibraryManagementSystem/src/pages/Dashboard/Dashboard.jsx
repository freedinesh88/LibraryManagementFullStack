import React from 'react'
import StateCard from './StateCard'

const Dashboard = () => {
  return (
    <div className='min-h-screen bg-gradient-to-r from-indigo-50 via-white to bg-purple-500 py-8'>
        <div className='max-w-7xl px-4 sm:px-6 lg:px-8'>
            <div className='mb-8 animate-fade-in-up'>
                <h1 className='text-4xl font-bold text-indigo-500 mb-2'>My {" "}
                    <span className='bg-gradient-to-r from-indigo-600 to-purple-600 bg-clip-text text-transparent'>Dashboard</span></h1>

                    <p className='text-lg text-gray-600'>Track your reading journey and manage your library</p>


            </div>

            {/* state cards */}

            <div>
                {[1,1,1,1].map((item, index) => (
                    <StateCard key={index} />
                ))}
            </div>

        </div>
    </div>
  )
}

export default Dashboard
