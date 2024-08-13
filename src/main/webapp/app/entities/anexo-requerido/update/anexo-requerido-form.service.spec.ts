import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../anexo-requerido.test-samples';

import { AnexoRequeridoFormService } from './anexo-requerido-form.service';

describe('AnexoRequerido Form Service', () => {
  let service: AnexoRequeridoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AnexoRequeridoFormService);
  });

  describe('Service methods', () => {
    describe('createAnexoRequeridoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAnexoRequeridoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            tipo: expect.any(Object),
            descricao: expect.any(Object),
          }),
        );
      });

      it('passing IAnexoRequerido should create a new form with FormGroup', () => {
        const formGroup = service.createAnexoRequeridoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            tipo: expect.any(Object),
            descricao: expect.any(Object),
          }),
        );
      });
    });

    describe('getAnexoRequerido', () => {
      it('should return NewAnexoRequerido for default AnexoRequerido initial value', () => {
        const formGroup = service.createAnexoRequeridoFormGroup(sampleWithNewData);

        const anexoRequerido = service.getAnexoRequerido(formGroup) as any;

        expect(anexoRequerido).toMatchObject(sampleWithNewData);
      });

      it('should return NewAnexoRequerido for empty AnexoRequerido initial value', () => {
        const formGroup = service.createAnexoRequeridoFormGroup();

        const anexoRequerido = service.getAnexoRequerido(formGroup) as any;

        expect(anexoRequerido).toMatchObject({});
      });

      it('should return IAnexoRequerido', () => {
        const formGroup = service.createAnexoRequeridoFormGroup(sampleWithRequiredData);

        const anexoRequerido = service.getAnexoRequerido(formGroup) as any;

        expect(anexoRequerido).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAnexoRequerido should not enable id FormControl', () => {
        const formGroup = service.createAnexoRequeridoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAnexoRequerido should disable id FormControl', () => {
        const formGroup = service.createAnexoRequeridoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
