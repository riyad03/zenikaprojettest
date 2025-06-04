import React from "react";
import { Link } from "react-router-dom";

function Header() {
  return (
    <header className="bg-gray-50 border-b border-gray-100">
      <nav className="mx-auto px-6 py-4">
        <div className="flex justify-between items-center">
          
          <div className="text-2xl font-light text-gray-800">
            Zenika Test
          </div>

         
          <div className="flex space-x-8">
            <Link
              to="/"
              className="text-gray-600 hover:text-gray-900 text-sm tracking-wide transition-colors duration-200"
            >
              Accueil
            </Link>
            <Link
              to="/panier"
              className="text-gray-600 hover:text-gray-900 text-sm tracking-wide transition-colors duration-200"
            >
              Panier
            </Link>
          </div>
        </div>
      </nav>
    </header>
  );
}

export default Header;
