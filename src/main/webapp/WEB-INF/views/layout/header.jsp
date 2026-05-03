<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Library Management System</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, Helvetica, sans-serif; background: #f4f5f7; color: #333; }
        nav {
            background: #003366;
            padding: 0 2rem;
            display: flex;
            align-items: center;
            justify-content: space-between;
            height: 52px;
            border-bottom: 3px solid #002244;
        }
        .nav-brand {
            color: #ffffff;
            font-size: 1.2rem;
            font-weight: bold;
            text-decoration: none;
        }
        .nav-links { display: flex; gap: 0.3rem; }
        .nav-links a {
            color: #ccd9e8;
            text-decoration: none;
            padding: 0.4rem 0.85rem;
            font-size: 0.92rem;
            border: 1px solid transparent;
            border-radius: 3px;
        }
        .nav-links a:hover, .nav-links a.active {
            background: #002244;
            color: #ffffff;
        }
        .container { max-width: 1100px; margin: 1.5rem auto; padding: 0 1.5rem; }
        .page-header {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-bottom: 1.2rem;
            padding-bottom: 0.75rem;
            border-bottom: 2px solid #003366;
        }
        .page-header h1 { font-size: 1.5rem; color: #003366; font-weight: bold; }
        .btn {
            display: inline-block;
            padding: 0.45rem 1rem;
            border: 1px solid transparent;
            cursor: pointer;
            font-size: 0.88rem;
            font-weight: 600;
            text-decoration: none;
            border-radius: 3px;
        }
        .btn-primary { background: #003366; color: #fff; border-color: #002244; }
        .btn-primary:hover { background: #002244; }
        .btn-secondary { background: #555; color: #fff; border-color: #444; }
        .btn-secondary:hover { background: #444; }
        .btn-warning { background: #cc8800; color: #fff; border-color: #aa7000; }
        .btn-warning:hover { background: #aa7000; }
        .btn-danger { background: #cc2200; color: #fff; border-color: #aa1800; }
        .btn-danger:hover { background: #aa1800; }
        .btn-sm { padding: 0.3rem 0.65rem; font-size: 0.8rem; }
        .alert {
            padding: 0.75rem 1rem;
            border: 1px solid;
            border-radius: 3px;
            margin-bottom: 1rem;
            font-size: 0.9rem;
        }
        .alert-success { background: #d4edda; color: #155724; border-color: #c3e6cb; }
        .alert-error { background: #f8d7da; color: #721c24; border-color: #f5c6cb; }
        .card {
            background: #fff;
            border: 1px solid #ccc;
            border-radius: 3px;
        }
        table { width: 100%; border-collapse: collapse; }
        thead { background: #003366; color: #fff; }
        thead th { padding: 0.7rem 1rem; text-align: left; font-size: 0.88rem; font-weight: bold; }
        tbody tr { border-bottom: 1px solid #e8e8e8; }
        tbody tr:hover { background: #f0f4f8; }
        tbody td { padding: 0.65rem 1rem; font-size: 0.9rem; vertical-align: middle; }
        .badge {
            display: inline-block;
            padding: 0.18rem 0.55rem;
            font-size: 0.78rem;
            font-weight: 600;
            background: #e8edf0;
            color: #444;
            border: 1px solid #ccc;
            border-radius: 2px;
        }
        .badge-fantasy { background: #e8edf0; color: #444; border-color: #ccc; }
        .badge-mystery { background: #e8edf0; color: #444; border-color: #ccc; }
        .badge-dystopian { background: #e8edf0; color: #444; border-color: #ccc; }
        .badge-satire { background: #e8edf0; color: #444; border-color: #ccc; }
        .badge-literary { background: #e8edf0; color: #444; border-color: #ccc; }
        .badge-default { background: #e8edf0; color: #4a5568; border-color: #ccc; }
        .form-container { padding: 1.5rem; }
        .form-group { margin-bottom: 1.1rem; }
        .form-group label { display: block; margin-bottom: 0.35rem; font-weight: bold; color: #333; font-size: 0.88rem; }
        .form-group input, .form-group select {
            width: 100%;
            padding: 0.5rem 0.75rem;
            border: 1px solid #bbb;
            border-radius: 3px;
            font-size: 0.9rem;
            outline: none;
            background: #fff;
        }
        .form-group input:focus, .form-group select:focus { border-color: #003366; }
        .form-actions { display: flex; gap: 0.8rem; margin-top: 1.5rem; }
        .stat-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(180px, 1fr)); gap: 1rem; margin-bottom: 1.5rem; }
        .stat-card {
            background: #fff;
            border: 1px solid #ccc;
            border-radius: 3px;
            padding: 1.1rem 1.3rem;
            display: flex;
            align-items: center;
            gap: 1rem;
        }
        .stat-icon {
            width: 44px;
            height: 44px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 0.75rem;
            font-weight: bold;
            color: #fff;
            border-radius: 2px;
            flex-shrink: 0;
            text-align: center;
        }
        .stat-icon-red { background: #003366; }
        .stat-icon-blue { background: #003366; }
        .stat-number { font-size: 1.6rem; font-weight: bold; color: #003366; line-height: 1; }
        .stat-label { font-size: 0.8rem; color: #666; margin-top: 0.2rem; }
        .actions-cell { display: flex; gap: 0.35rem; }
        .empty-state { text-align: center; padding: 2.5rem; color: #888; }
        .empty-state p { font-size: 0.95rem; }
    </style>
</head>
<body>
<nav>
    <a class="nav-brand" href="/">Library Management System</a>
    <div class="nav-links">
        <a href="/" ${pageTitle == 'Home' ? 'class="active"' : ''}>Home</a>
        <a href="/authors" ${pageTitle == 'Authors' ? 'class="active"' : ''}>Authors</a>
        <a href="/books" ${pageTitle == 'Books' ? 'class="active"' : ''}>Books</a>
    </div>
</nav>
