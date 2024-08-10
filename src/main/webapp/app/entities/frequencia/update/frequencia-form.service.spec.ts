import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../frequencia.test-samples';

import { FrequenciaFormService } from './frequencia-form.service';

describe('Frequencia Form Service', () => {
  let service: FrequenciaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FrequenciaFormService);
  });

  describe('Service methods', () => {
    describe('createFrequenciaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFrequenciaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            prioridade: expect.any(Object),
            descricao: expect.any(Object),
            numeroDias: expect.any(Object),
          }),
        );
      });

      it('passing IFrequencia should create a new form with FormGroup', () => {
        const formGroup = service.createFrequenciaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            prioridade: expect.any(Object),
            descricao: expect.any(Object),
            numeroDias: expect.any(Object),
          }),
        );
      });
    });

    describe('getFrequencia', () => {
      it('should return NewFrequencia for default Frequencia initial value', () => {
        const formGroup = service.createFrequenciaFormGroup(sampleWithNewData);

        const frequencia = service.getFrequencia(formGroup) as any;

        expect(frequencia).toMatchObject(sampleWithNewData);
      });

      it('should return NewFrequencia for empty Frequencia initial value', () => {
        const formGroup = service.createFrequenciaFormGroup();

        const frequencia = service.getFrequencia(formGroup) as any;

        expect(frequencia).toMatchObject({});
      });

      it('should return IFrequencia', () => {
        const formGroup = service.createFrequenciaFormGroup(sampleWithRequiredData);

        const frequencia = service.getFrequencia(formGroup) as any;

        expect(frequencia).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFrequencia should not enable id FormControl', () => {
        const formGroup = service.createFrequenciaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFrequencia should disable id FormControl', () => {
        const formGroup = service.createFrequenciaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
