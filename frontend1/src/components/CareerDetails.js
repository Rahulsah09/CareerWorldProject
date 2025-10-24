import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, Link } from 'react-router-dom';

const CareerDetails = () => {
  const { id } = useParams();
  const [career, setCareer] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchCareer();
  }, [id]);

  const fetchCareer = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/api/careers/${id}`);
      setCareer(response.data);
      setLoading(false);
    } catch (error) {
      console.error('Error fetching career:', error);
      setLoading(false);
    }
  };

  if (loading) {
    return <div className="text-center"><h3>Loading career details...</h3></div>;
  }

  if (!career) {
    return <div className="text-center"><h3>Career not found</h3></div>;
  }

  return (
    <div>
      <Link to="/careers" className="btn btn-secondary mb-3">← Back to Careers</Link>
      <div className="card">
        <div className="card-body">
          <h2 className="card-title">{career.title}</h2>
          <p className="card-text">{career.description}</p>
          <div className="row">
            <div className="col-md-6">
              <h5>Salary Range</h5>
              <p>{career.salaryRange}</p>
            </div>
            <div className="col-md-6">
              <h5>Education Required</h5>
              <p>{career.educationRequired}</p>
            </div>
          </div>
          <h5>Skills Required</h5>
          <p>{career.skillsRequired}</p>
          
          {career.tags && (
            <div>
              <h5>Career Tags</h5>
              <div className="mt-2">
                {career.tags.split(',').map((tag, index) => (
                  <span key={index} className="badge bg-primary me-2 mb-2">
                    {tag.trim()}
                  </span>
                ))}
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default CareerDetails;