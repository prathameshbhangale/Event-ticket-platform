import React from "react";

export default function Home() {
  return (
    <div className="min-h-screen bg-gray-100 text-gray-800">
      {/* Hero Section */}
      <section className="flex flex-col items-center justify-center text-center py-20 bg-gray-200">
        <h2 className="text-4xl font-semibold mb-4">Discover and Join Events Near You</h2>
        <p className="text-gray-600 mb-6 max-w-xl">
          Find local and virtual events tailored to your interests. Connect with communities and expand your network with EventHub.
        </p>
        <button className="bg-gray-800 text-white px-6 py-2 rounded-lg hover:bg-gray-700 transition">
          Browse Events
        </button>
      </section>

      {/* Features Section */}
      <section className="py-16 px-6 md:px-20">
        <h3 className="text-3xl font-semibold text-center mb-10">Features</h3>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
          <div className="bg-white rounded-xl shadow p-6">
            <h4 className="text-xl font-semibold mb-2">Curated Events</h4>
            <p className="text-gray-600">Get personalized recommendations based on your interests and location.</p>
          </div>
          <div className="bg-white rounded-xl shadow p-6">
            <h4 className="text-xl font-semibold mb-2">Easy Booking</h4>
            <p className="text-gray-600">Register and manage your event bookings with just a few clicks.</p>
          </div>
          <div className="bg-white rounded-xl shadow p-6">
            <h4 className="text-xl font-semibold mb-2">Community Focused</h4>
            <p className="text-gray-600">Meet like-minded individuals and build lasting connections.</p>
          </div>
        </div>
      </section>

      {/* Who We Are Section */}
      <section className="py-16 px-6 md:px-20 bg-gray-50">
        <h3 className="text-3xl font-semibold text-center mb-8">Who We Are</h3>
        <div className="max-w-4xl mx-auto text-center text-gray-700">
          <p>
            EventHub is a community-driven platform dedicated to helping people discover events they love. From tech meetups to art exhibitions,
            we bring together diverse communities through memorable experiences. Our mission is to make event discovery and participation
            seamless, accessible, and rewarding for everyone.
          </p>
        </div>
      </section>

      {/* Footer */}
      <footer className="bg-white text-center text-gray-600 py-6 border-t">
        <p className="text-sm">&copy; 2025 EventHub. All rights reserved.</p>
      </footer>
    </div>
  );
}
