import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../ator-avaliado.test-samples';

import { AtorAvaliadoFormService } from './ator-avaliado-form.service';

describe('AtorAvaliado Form Service', () => {
  let service: AtorAvaliadoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AtorAvaliadoFormService);
  });

  describe('Service methods', () => {
    describe('createAtorAvaliadoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAtorAvaliadoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            ativo: expect.any(Object),
          }),
        );
      });

      it('passing IAtorAvaliado should create a new form with FormGroup', () => {
        const formGroup = service.createAtorAvaliadoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            ativo: expect.any(Object),
          }),
        );
      });
    });

    describe('getAtorAvaliado', () => {
      it('should return NewAtorAvaliado for default AtorAvaliado initial value', () => {
        const formGroup = service.createAtorAvaliadoFormGroup(sampleWithNewData);

        const atorAvaliado = service.getAtorAvaliado(formGroup) as any;

        expect(atorAvaliado).toMatchObject(sampleWithNewData);
      });

      it('should return NewAtorAvaliado for empty AtorAvaliado initial value', () => {
        const formGroup = service.createAtorAvaliadoFormGroup();

        const atorAvaliado = service.getAtorAvaliado(formGroup) as any;

        expect(atorAvaliado).toMatchObject({});
      });

      it('should return IAtorAvaliado', () => {
        const formGroup = service.createAtorAvaliadoFormGroup(sampleWithRequiredData);

        const atorAvaliado = service.getAtorAvaliado(formGroup) as any;

        expect(atorAvaliado).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAtorAvaliado should not enable id FormControl', () => {
        const formGroup = service.createAtorAvaliadoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAtorAvaliado should disable id FormControl', () => {
        const formGroup = service.createAtorAvaliadoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
