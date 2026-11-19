# CRUD de Estudiante - Patrones Comportamentales (2.4.2, 2.4.3 y 2.4.4)

**Grupo 5** — Allauca Steven, Gualotuña Brayan, Rodríguez David  
**Asignatura:** Análisis y Diseño de Software (NRC: 36839)  
**Docente:** Ing. Jenny A. Ruiz R., MIS

## Patrones implementados
- **2.4.2 Observer (`Observador`):** `SujetoEventosEstudiante`, `ObservadorEstudiante`, `EventoEstudiante`, `TipoEventoEstudiante`, `ObservadorAuditoriaEstudiante`, `ObservadorNotificacionEstudiante`.
- **2.4.3 Strategy (`Estrategia`):** `EstrategiaBusquedaEstudiante`, `BusquedaPorIdStrategy`, `BusquedaPorNombreStrategy`.
- **2.4.4 Command (`Comando`):** `ComandoCrudEstudiante`, `ComandoRegistrarEstudiante`, `ComandoActualizarEstudiante`, `ComandoEliminarEstudiante`, `InvocadorComandosEstudiante`.

## Ejecución desde PowerShell
Ubicarse en `Parcial 2`:
```powershell
javac (Get-ChildItem "CRUD_Estudiante_Patrones" -Recurse -Filter *.java | Select-Object -ExpandProperty FullName)
java CRUD_Estudiante_Patrones.presentacion.PruebaPatronesComportamentales
java CRUD_Estudiante_Patrones.presentacion.CRUDEstudiantesGUI
```
