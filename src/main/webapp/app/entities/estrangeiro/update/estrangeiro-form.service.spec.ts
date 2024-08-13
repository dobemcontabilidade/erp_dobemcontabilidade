import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../estrangeiro.test-samples';

import { EstrangeiroFormService } from './estrangeiro-form.service';

describe('Estrangeiro Form Service', () => {
  let service: EstrangeiroFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EstrangeiroFormService);
  });

  describe('Service methods', () => {
    describe('createEstrangeiroFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEstrangeiroFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dataChegada: expect.any(Object),
            dataNaturalizacao: expect.any(Object),
            casadoComBrasileiro: expect.any(Object),
            filhosComBrasileiro: expect.any(Object),
            checked: expect.any(Object),
            funcionario: expect.any(Object),
          }),
        );
      });

      it('passing IEstrangeiro should create a new form with FormGroup', () => {
        const formGroup = service.createEstrangeiroFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            dataChegada: expect.any(Object),
            dataNaturalizacao: expect.any(Object),
            casadoComBrasileiro: expect.any(Object),
            filhosComBrasileiro: expect.any(Object),
            checked: expect.any(Object),
            funcionario: expect.any(Object),
          }),
        );
      });
    });

    describe('getEstrangeiro', () => {
      it('should return NewEstrangeiro for default Estrangeiro initial value', () => {
        const formGroup = service.createEstrangeiroFormGroup(sampleWithNewData);

        const estrangeiro = service.getEstrangeiro(formGroup) as any;

        expect(estrangeiro).toMatchObject(sampleWithNewData);
      });

      it('should return NewEstrangeiro for empty Estrangeiro initial value', () => {
        const formGroup = service.createEstrangeiroFormGroup();

        const estrangeiro = service.getEstrangeiro(formGroup) as any;

        expect(estrangeiro).toMatchObject({});
      });

      it('should return IEstrangeiro', () => {
        const formGroup = service.createEstrangeiroFormGroup(sampleWithRequiredData);

        const estrangeiro = service.getEstrangeiro(formGroup) as any;

        expect(estrangeiro).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEstrangeiro should not enable id FormControl', () => {
        const formGroup = service.createEstrangeiroFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEstrangeiro should disable id FormControl', () => {
        const formGroup = service.createEstrangeiroFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
