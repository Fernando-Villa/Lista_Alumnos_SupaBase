-- Ejecutar en Supabase > SQL Editor

create table if not exists public.alumnos (
  id         bigint generated always as identity primary key,
  created_at timestamptz not null default now(),
  nombres    text not null,
  paralelo   text,
  correo     text,
  telefono   text,
  foto       text
);

-- RLS activado: sin política, nadie puede leer la tabla.
alter table public.alumnos enable row level security;

-- Solo LECTURA con la clave publicable (rol anon). No se permite insertar, editar ni borrar.
create policy "alumnos_select_anon"
  on public.alumnos
  for select
  to anon
  using (true);

-- Datos de ejemplo (usar solo información autorizada por los compañeros)
insert into public.alumnos (nombres, paralelo, correo, telefono) values
  ('Ana Pérez',   'A', 'ana.perez@ejemplo.com',   '0990000001'),
  ('Luis Mora',   'A', 'luis.mora@ejemplo.com',   '0990000002'),
  ('María Vera',  'B', 'maria.vera@ejemplo.com',  '0990000003');
