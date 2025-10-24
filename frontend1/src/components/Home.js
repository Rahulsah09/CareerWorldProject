import React from 'react';
import { Link } from 'react-router-dom';

const Home = () => {
  return (
    <div className="text-center">
      <h1 className="display-4 mb-4"> Welcome to CareerCompass</h1>
      <p className="lead mb-4">
        Discover your ideal career path with our comprehensive career guidance system.
        Take our assessment to get personalized career recommendations.
      </p>
      <div className="row mt-5">
        <div className="col-md-4">
          <div className="card">
            <div className="card-body">
              <h5 className="card-title">Career Assessment</h5>
              <p className="card-text">Take our career assessment to discover suitable career paths.</p>
              <Link to="/assessment" className="btn btn-primary">Start Assessment</Link>
            </div>
          </div>
        </div>
        <div className="col-md-4">
          <div className="card">
            <div className="card-body">
              <h5 className="card-title">Explore Careers</h5>
              <p className="card-text">Browse through various career options and their details.</p>
              <Link to="/careers" className="btn btn-success">View Careers</Link>
            </div>
          </div>
        </div>
        <div className="col-md-4">
          <div className="card">
            <div className="card-body">
              <h5 className="card-title">Personalized Guidance</h5>
              <p className="card-text">Get personalized career recommendations based on your profile.</p>
              <Link to="/assessment" className="btn btn-info">Get Started</Link>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Home;