import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../tarefa-ordem-servico.test-samples';

import { TarefaOrdemServicoFormService } from './tarefa-ordem-servico-form.service';

describe('TarefaOrdemServico Form Service', () => {
  let service: TarefaOrdemServicoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TarefaOrdemServicoFormService);
  });

  describe('Service methods', () => {
    describe('createTarefaOrdemServicoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTarefaOrdemServicoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            notificarCliente: expect.any(Object),
            notificarContador: expect.any(Object),
            anoReferencia: expect.any(Object),
            dataAdmin: expect.any(Object),
            servicoContabilOrdemServico: expect.any(Object),
          }),
        );
      });

      it('passing ITarefaOrdemServico should create a new form with FormGroup', () => {
        const formGroup = service.createTarefaOrdemServicoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            notificarCliente: expect.any(Object),
            notificarContador: expect.any(Object),
            anoReferencia: expect.any(Object),
            dataAdmin: expect.any(Object),
            servicoContabilOrdemServico: expect.any(Object),
          }),
        );
      });
    });

    describe('getTarefaOrdemServico', () => {
      it('should return NewTarefaOrdemServico for default TarefaOrdemServico initial value', () => {
        const formGroup = service.createTarefaOrdemServicoFormGroup(sampleWithNewData);

        const tarefaOrdemServico = service.getTarefaOrdemServico(formGroup) as any;

        expect(tarefaOrdemServico).toMatchObject(sampleWithNewData);
      });

      it('should return NewTarefaOrdemServico for empty TarefaOrdemServico initial value', () => {
        const formGroup = service.createTarefaOrdemServicoFormGroup();

        const tarefaOrdemServico = service.getTarefaOrdemServico(formGroup) as any;

        expect(tarefaOrdemServico).toMatchObject({});
      });

      it('should return ITarefaOrdemServico', () => {
        const formGroup = service.createTarefaOrdemServicoFormGroup(sampleWithRequiredData);

        const tarefaOrdemServico = service.getTarefaOrdemServico(formGroup) as any;

        expect(tarefaOrdemServico).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITarefaOrdemServico should not enable id FormControl', () => {
        const formGroup = service.createTarefaOrdemServicoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTarefaOrdemServico should disable id FormControl', () => {
        const formGroup = service.createTarefaOrdemServicoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
