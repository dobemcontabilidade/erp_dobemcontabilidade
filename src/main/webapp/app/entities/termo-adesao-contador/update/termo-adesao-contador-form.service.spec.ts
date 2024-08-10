import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../termo-adesao-contador.test-samples';

import { TermoAdesaoContadorFormService } from './termo-adesao-contador-form.service';

describe('TermoAdesaoContador Form Service', () => {
  let service: TermoAdesaoContadorFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TermoAdesaoContadorFormService);
  });

  describe('Service methods', () => {
    describe('createTermoAdesaoContadorFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTermoAdesaoContadorFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dataAdesao: expect.any(Object),
            contador: expect.any(Object),
            termoDeAdesao: expect.any(Object),
          }),
        );
      });

      it('passing ITermoAdesaoContador should create a new form with FormGroup', () => {
        const formGroup = service.createTermoAdesaoContadorFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dataAdesao: expect.any(Object),
            contador: expect.any(Object),
            termoDeAdesao: expect.any(Object),
          }),
        );
      });
    });

    describe('getTermoAdesaoContador', () => {
      it('should return NewTermoAdesaoContador for default TermoAdesaoContador initial value', () => {
        const formGroup = service.createTermoAdesaoContadorFormGroup(sampleWithNewData);

        const termoAdesaoContador = service.getTermoAdesaoContador(formGroup) as any;

        expect(termoAdesaoContador).toMatchObject(sampleWithNewData);
      });

      it('should return NewTermoAdesaoContador for empty TermoAdesaoContador initial value', () => {
        const formGroup = service.createTermoAdesaoContadorFormGroup();

        const termoAdesaoContador = service.getTermoAdesaoContador(formGroup) as any;

        expect(termoAdesaoContador).toMatchObject({});
      });

      it('should return ITermoAdesaoContador', () => {
        const formGroup = service.createTermoAdesaoContadorFormGroup(sampleWithRequiredData);

        const termoAdesaoContador = service.getTermoAdesaoContador(formGroup) as any;

        expect(termoAdesaoContador).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITermoAdesaoContador should not enable id FormControl', () => {
        const formGroup = service.createTermoAdesaoContadorFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTermoAdesaoContador should disable id FormControl', () => {
        const formGroup = service.createTermoAdesaoContadorFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
