import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

const CareerList = () => {
  const [careers, setCareers] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchCareers();
  }, []);

  const fetchCareers = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/careers');
      setCareers(response.data);
      setLoading(false);
    } catch (error) {
      console.error('Error fetching careers:', error);
      setLoading(false);
    }
  };

  if (loading) {
    return <div className="text-center"><h3>Loading careers...</h3></div>;
  }

  return (
    <div className="container-fluid">
      <div className="row">
        <div className="col-12">
          <h2 className="mb-4">Career Options ({careers.length} careers available)</h2>
          
          {careers.length === 0 && (
            <div className="alert alert-warning">
              <h4>No careers found!</h4>
              <p>Please make sure the backend server is running and try refreshing the page.</p>
              <button onClick={fetchCareers} className="btn btn-primary">Refresh</button>
            </div>
          )}
          
          <div className="row">
            {careers.map(career => (
              <div key={career.id} className="col-lg-4 col-md-6 mb-4">
                <div className="card h-100">
                  <div className="card-body d-flex flex-column">
                    <h5 className="card-title">{career.title}</h5>
                    <p className="card-text flex-grow-1">{career.description}</p>
                    
                    <div className="mt-auto">
                      <p><strong>Salary Range:</strong> {career.salaryRange}</p>
                      <p><strong>Education Required:</strong> {career.educationRequired}</p>
                      
                      {career.tags && (
                        <div className="mb-3">
                          <strong>Tags:</strong>
                          <div className="mt-1">
                            {career.tags.split(',').map((tag, index) => (
                              <span key={index} className="badge bg-secondary me-1 mb-1">
                                {tag.trim()}
                              </span>
                            ))}
                          </div>
                        </div>
                      )}
                      
                      <div className="d-grid">
                        <Link to={`/career/${career.id}`} className="btn btn-primary">View Details</Link>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default CareerList;