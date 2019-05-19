--mesa
INSERT INTO public.mesa(
            idmesa, nummaxcomensales, ubicacion)
    VALUES (nextval('mesa_idmesa_seq'), '4', 1);
    INSERT INTO public.mesa(
            idmesa, nummaxcomensales, ubicacion)
    VALUES (nextval('mesa_idmesa_seq'), '6', 2);
    INSERT INTO public.mesa(
            idmesa, nummaxcomensales, ubicacion)
    VALUES (nextval('mesa_idmesa_seq'), '2', 3);
    INSERT INTO public.mesa(
            idmesa, nummaxcomensales, ubicacion)
    VALUES (nextval('mesa_idmesa_seq'), '3', 4);
    INSERT INTO public.mesa(
            idmesa, nummaxcomensales, ubicacion)
    VALUES (nextval('mesa_idmesa_seq'), '5', 5);
--cocinero    
    INSERT INTO public.cocinero(
            idcocinero, apellido1, apellido2, nombre)
    VALUES (nextval('cocinero_idcocinero_seq'::regclass), 'Gomez', '', 'Andres');
    INSERT INTO public.cocinero(
            idcocinero, apellido1, apellido2, nombre)
    VALUES (nextval('cocinero_idcocinero_seq'::regclass), 'Gomez', '', 'Ernesto');
    INSERT INTO public.cocinero(
            idcocinero, apellido1, apellido2, nombre)
    VALUES (nextval('cocinero_idcocinero_seq'::regclass), 'Gomez', '', 'Daniel');
--camarero
    INSERT INTO public.camarero(
            idcamarero, apellido1, apellido2, nombre)
    VALUES (nextval('camarero_idcamarero_seq'::regclass), 'Perez', '', 'Andrea');
    INSERT INTO public.camarero(
            idcamarero, apellido1, apellido2, nombre)
    VALUES (nextval('camarero_idcamarero_seq'::regclass), 'Sanchez', '', 'Luis');
    INSERT INTO public.camarero(
            idcamarero, apellido1, apellido2, nombre)
    VALUES (nextval('camarero_idcamarero_seq'::regclass), 'Garcia', '', 'Maria');
    INSERT INTO public.camarero(
            idcamarero, apellido1, apellido2, nombre)
    VALUES (nextval('camarero_idcamarero_seq'::regclass), 'Lopez', '', 'Carlos');
    INSERT INTO public.camarero(
            idcamarero, apellido1, apellido2, nombre)
    VALUES (nextval('camarero_idcamarero_seq'::regclass), 'Rodriguez', '', 'Sandra');
