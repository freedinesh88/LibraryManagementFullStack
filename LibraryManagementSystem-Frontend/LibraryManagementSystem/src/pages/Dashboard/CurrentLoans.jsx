import React from 'react'
import CurrentLoanCard from './CurrentLoanCard'

const loan = {
  bookTitle: "The Great Gatsby",
  bookAuthor: "F. Scott Fitzgerald",
  bookCoverImage: "https://images-na.ssl-images-amazon.com/images/I/81af+MCATTL.jpg",
  dueDate: "2023-12-31",
  status: "ACTIVE",
  remainingDays: 5,
  overdueDays: 0
}

const CurrentLoans = () => {
  return (
    <div className='p-6'>
        <h3 className='text-2xl font-bold text-gray-900 mb-6'>Books You are Currently Reading</h3>
        <div>
            {[1,1,1,1].map((item, index) => <CurrentLoanCard loan={loan} key={index} />)}
        </div>
    </div>
  )
}

export default CurrentLoans
