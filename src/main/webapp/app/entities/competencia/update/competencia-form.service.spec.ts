import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../competencia.test-samples';

import { CompetenciaFormService } from './competencia-form.service';

describe('Competencia Form Service', () => {
  let service: CompetenciaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CompetenciaFormService);
  });

  describe('Service methods', () => {
    describe('createCompetenciaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCompetenciaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            numero: expect.any(Object),
          }),
        );
      });

      it('passing ICompetencia should create a new form with FormGroup', () => {
        const formGroup = service.createCompetenciaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            numero: expect.any(Object),
          }),
        );
      });
    });

    describe('getCompetencia', () => {
      it('should return NewCompetencia for default Competencia initial value', () => {
        const formGroup = service.createCompetenciaFormGroup(sampleWithNewData);

        const competencia = service.getCompetencia(formGroup) as any;

        expect(competencia).toMatchObject(sampleWithNewData);
      });

      it('should return NewCompetencia for empty Competencia initial value', () => {
        const formGroup = service.createCompetenciaFormGroup();

        const competencia = service.getCompetencia(formGroup) as any;

        expect(competencia).toMatchObject({});
      });

      it('should return ICompetencia', () => {
        const formGroup = service.createCompetenciaFormGroup(sampleWithRequiredData);

        const competencia = service.getCompetencia(formGroup) as any;

        expect(competencia).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICompetencia should not enable id FormControl', () => {
        const formGroup = service.createCompetenciaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCompetencia should disable id FormControl', () => {
        const formGroup = service.createCompetenciaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
