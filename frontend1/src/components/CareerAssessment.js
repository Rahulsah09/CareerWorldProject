import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const CareerAssessment = () => {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    question1: '',
    question2: '',
    question3: '',
    question4: ''
  });
  const [submitted, setSubmitted] = useState(false);
  const [recommendations, setRecommendations] = useState([]);
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    const assessmentData = {
      user: {
        name: formData.name,
        email: formData.email
      },
      question1: formData.question1,
      question2: formData.question2,
      question3: formData.question3,
      question4: formData.question4
    };

    try {
      // Submit the assessment
      await axios.post('http://localhost:8080/api/assessments', assessmentData);
      
      // Get top 3 career recommendations
      const recommendationsResponse = await axios.post('http://localhost:8080/api/assessments/recommendations', assessmentData);
      setRecommendations(recommendationsResponse.data);
      setSubmitted(true);
    } catch (error) {
      console.error('Error submitting assessment:', error);
      alert('Error submitting assessment. Please try again.');
    }
  };

  const handleNewAssessment = () => {
    setSubmitted(false);
    setRecommendations([]);
    setFormData({
      name: '',
      email: '',
      question1: '',
      question2: '',
      question3: '',
      question4: ''
    });
  };

  if (submitted) {
    return (
      <div>
        <div className="text-center mb-4">
          <h2>Career Assessment Results</h2>
          <p className="lead">Based on your responses, here are your top career matches:</p>
        </div>
        
        <div className="row">
          {recommendations.map((career, index) => (
            <div key={career.id} className="col-md-4 mb-4">
              <div className={`card h-100 ${index === 0 ? 'border-primary' : ''}`}>
                {index === 0 && (
                  <div className="card-header bg-primary text-white text-center">
                    <strong>Top Match</strong>
                  </div>
                )}
                <div className="card-body d-flex flex-column">
                  <h5 className="card-title">{career.title}</h5>
                  <p className="card-text flex-grow-1">{career.description}</p>
                  
                  <div className="mt-auto">
                    <div className="mb-2">
                      <small className="text-muted">
                        <strong>Salary Range:</strong> {career.salaryRange}
                      </small>
                    </div>
                    <div className="mb-2">
                      <small className="text-muted">
                        <strong>Education:</strong> {career.educationRequired}
                      </small>
                    </div>
                    <div className="mb-3">
                      <small className="text-muted">
                        <strong>Skills:</strong> {career.skillsRequired}
                      </small>
                    </div>
                    
                    {career.tags && (
                      <div className="mb-3">
                        <small className="text-muted">
                          <strong>Tags:</strong>
                          <div className="mt-1">
                            {career.tags.split(',').map((tag, tagIndex) => (
                              <span key={tagIndex} className="badge bg-secondary me-1 mb-1">
                                {tag.trim()}
                              </span>
                            ))}
                          </div>
                        </small>
                      </div>
                    )}
                  </div>
                </div>
                <div className="card-footer bg-transparent">
                  <div className="d-grid">
                    <button 
                      className="btn btn-outline-primary btn-sm"
                      onClick={() => navigate(`/career/${career.id}`)}
                    >
                      View Details
                    </button>
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>

        <div className="text-center mt-4">
          <button onClick={() => navigate('/careers')} className="btn btn-primary me-2">
            Explore All Careers
          </button>
          <button onClick={handleNewAssessment} className="btn btn-secondary">
            Take New Assessment
          </button>
        </div>
      </div>
    );
  }

  return (
    <div>
      <h2 className="mb-4">Career Assessment</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label htmlFor="name" className="form-label">Full Name</label>
          <input
            type="text"
            className="form-control"
            id="name"
            name="name"
            value={formData.name}
            onChange={handleChange}
            required
          />
        </div>
        <div className="mb-3">
          <label htmlFor="email" className="form-label">Email</label>
          <input
            type="email"
            className="form-control"
            id="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            required
          />
        </div>
        <div className="mb-3">
          <label htmlFor="question1" className="form-label">
            What are your main interests or passions?
          </label>
          <textarea
            className="form-control"
            id="question1"
            name="question1"
            value={formData.question1}
            onChange={handleChange}
            rows="3"
            required
          ></textarea>
        </div>
        <div className="mb-3">
          <label htmlFor="question2" className="form-label">
            What type of work environment do you prefer?
          </label>
          <select
            className="form-control"
            id="question2"
            name="question2"
            value={formData.question2}
            onChange={handleChange}
            required
          >
            <option value="">Select an option</option>
            <option value="office">Office Environment</option>
            <option value="remote">Remote Work</option>
            <option value="field">Field Work</option>
            <option value="creative">Creative Studio</option>
          </select>
        </div>
        <div className="mb-3">
          <label htmlFor="question3" className="form-label">
            What is your expected salary range?
          </label>
          <select
            className="form-control"
            id="question3"
            name="question3"
            value={formData.question3}
            onChange={handleChange}
            required
          >
            <option value="">Select an option</option>
            <option value="30000-50000">$30,000 - $50,000</option>
            <option value="50000-80000">$50,000 - $80,000</option>
            <option value="80000-120000">$80,000 - $120,000</option>
            <option value="120000+">$120,000+</option>
          </select>
        </div>
        <div className="mb-3">
          <label htmlFor="question4" className="form-label">
            What is your highest level of education?
          </label>
          <select
            className="form-control"
            id="question4"
            name="question4"
            value={formData.question4}
            onChange={handleChange}
            required
          >
            <option value="">Select an option</option>
            <option value="highschool">High School</option>
            <option value="bachelors">Bachelor's Degree</option>
            <option value="masters">Master's Degree</option>
            <option value="phd">PhD</option>
          </select>
        </div>
        <button type="submit" className="btn btn-primary">Get Career Recommendation</button>
      </form>
    </div>
  );
};

export default CareerAssessment;