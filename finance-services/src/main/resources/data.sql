INSERT INTO tax_rates (tax_name, percentage) VALUES
    ('PDV 20%', 20.0),
    ('Boravisna taksa', 5.0);

INSERT INTO payment_methods (method_name, is_active) VALUES
    ('Credit Card', true),
    ('PayPal', true),
    ('Cash', true);

INSERT INTO invoices (booking_id, subtotal, total_amount, status, tax_rate_id) VALUES
    (1, 100.0, 120.0, 'PAID', 1),
    (2, 200.0, 210.0, 'UNPAID', 2);

INSERT INTO transactions (amount, transaction_date, invoice_id, payment_method_id) VALUES
    (120.0, '2026-03-01 10:00:00', 1, 1),
    (50.0, '2026-03-02 15:30:00', 2, 2);