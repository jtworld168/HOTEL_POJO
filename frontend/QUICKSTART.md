# Quick Start Guide - Hotel Lobby Store Frontend

## Prerequisites
- Node.js >= 18
- npm >= 9
- Backend API running on http://localhost:8080

## Installation

```bash
cd frontend
npm install
```

## Development

```bash
npm run dev
```

The application will be available at http://localhost:3000

## Build for Production

```bash
npm run build
```

The production files will be in the `dist/` directory.

## Preview Production Build

```bash
npm run preview
```

## Project Structure

```
frontend/
├── src/
│   ├── api/              # API layer
│   ├── components/       # Reusable components
│   ├── views/           # Page components
│   ├── store/           # Pinia stores
│   ├── router/          # Vue Router config
│   ├── locales/         # i18n translations
│   └── utils/           # Utility functions
├── index.html           # Entry HTML
└── package.json         # Dependencies
```

## Key Features

### 🔐 Authentication
- Navigate to `/login` to access the login page
- Default test credentials (if using demo backend):
  - Username: `admin`
  - Password: `123456`
  - Role: admin (has employee pricing)

### 🛍️ Shopping Flow
1. Browse products on the home page
2. Click on a product to view details
3. Add to cart
4. View cart and adjust quantities
5. Checkout to create an order
6. View orders in "My Orders"

### 📸 Barcode Scanner
- Click the camera icon in the header
- Allow camera permissions
- Point camera at a product barcode
- Auto-redirect to product detail

### 🌍 Language Toggle
- Click the globe icon in the header
- Select between Chinese (中文) or English
- All UI text updates immediately

### 💰 Employee Pricing
- Login with employee or admin role
- Employee prices shown in green
- Regular prices shown with strikethrough

### 🎫 Coupons
- Navigate to "Coupons" page
- Click "Claim" to add coupons to your account
- View claimed coupons in "My Coupons" tab
- Use coupons during checkout

## API Configuration

The frontend connects to the backend at `http://localhost:8080` by default.

To change this, edit `src/api/request.ts`:

```typescript
const service: AxiosInstance = axios.create({
  baseURL: 'http://localhost:8080',  // Change this
  timeout: 10000,
})
```

Or use the Vite proxy in `vite.config.ts`:

```typescript
server: {
  proxy: {
    '/api': {
      target: 'http://localhost:8080',  // Change this
      changeOrigin: true,
    }
  }
}
```

## Troubleshooting

### "Network Error" when making requests
- Ensure backend is running on http://localhost:8080
- Check browser console for CORS errors
- Verify API endpoints are accessible

### Camera not working for barcode scanner
- Ensure you're using HTTPS or localhost
- Allow camera permissions when prompted
- Check browser console for errors

### Language not switching
- Clear localStorage and refresh
- Check browser console for errors
- Ensure i18n is properly initialized in main.ts

## Environment Variables

Create a `.env` file in the frontend directory:

```
VITE_API_BASE_URL=http://localhost:8080
```

Then update `src/api/request.ts` to use it:

```typescript
const service: AxiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080',
  timeout: 10000,
})
```

## Testing Accounts

If your backend has test data, you may have these accounts:

- **Admin**: `admin` / `123456` (full access + employee pricing)
- **Employee**: `employee` / `123456` (employee pricing)
- **Customer**: `customer` / `123456` (regular pricing)

## Support

For issues or questions:
1. Check the README.md for detailed documentation
2. Review the code comments in source files
3. Check browser console for error messages

## License

MIT
