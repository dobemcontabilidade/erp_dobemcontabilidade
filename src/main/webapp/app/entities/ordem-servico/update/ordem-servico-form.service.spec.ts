import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../ordem-servico.test-samples';

import { OrdemServicoFormService } from './ordem-servico-form.service';

describe('OrdemServico Form Service', () => {
  let service: OrdemServicoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(OrdemServicoFormService);
  });

  describe('Service methods', () => {
    describe('createOrdemServicoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createOrdemServicoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            valor: expect.any(Object),
            prazo: expect.any(Object),
            dataCriacao: expect.any(Object),
            dataHoraCancelamento: expect.any(Object),
            statusDaOS: expect.any(Object),
            descricao: expect.any(Object),
            empresa: expect.any(Object),
            contador: expect.any(Object),
            fluxoModelo: expect.any(Object),
          }),
        );
      });

      it('passing IOrdemServico should create a new form with FormGroup', () => {
        const formGroup = service.createOrdemServicoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            valor: expect.any(Object),
            prazo: expect.any(Object),
            dataCriacao: expect.any(Object),
            dataHoraCancelamento: expect.any(Object),
            statusDaOS: expect.any(Object),
            descricao: expect.any(Object),
            empresa: expect.any(Object),
            contador: expect.any(Object),
            fluxoModelo: expect.any(Object),
          }),
        );
      });
    });

    describe('getOrdemServico', () => {
      it('should return NewOrdemServico for default OrdemServico initial value', () => {
        const formGroup = service.createOrdemServicoFormGroup(sampleWithNewData);

        const ordemServico = service.getOrdemServico(formGroup) as any;

        expect(ordemServico).toMatchObject(sampleWithNewData);
      });

      it('should return NewOrdemServico for empty OrdemServico initial value', () => {
        const formGroup = service.createOrdemServicoFormGroup();

        const ordemServico = service.getOrdemServico(formGroup) as any;

        expect(ordemServico).toMatchObject({});
      });

      it('should return IOrdemServico', () => {
        const formGroup = service.createOrdemServicoFormGroup(sampleWithRequiredData);

        const ordemServico = service.getOrdemServico(formGroup) as any;

        expect(ordemServico).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IOrdemServico should not enable id FormControl', () => {
        const formGroup = service.createOrdemServicoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewOrdemServico should disable id FormControl', () => {
        const formGroup = service.createOrdemServicoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
