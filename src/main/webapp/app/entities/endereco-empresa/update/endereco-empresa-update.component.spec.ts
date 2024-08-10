import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { EmpresaService } from 'app/entities/empresa/service/empresa.service';
import { ICidade } from 'app/entities/cidade/cidade.model';
import { CidadeService } from 'app/entities/cidade/service/cidade.service';
import { IEnderecoEmpresa } from '../endereco-empresa.model';
import { EnderecoEmpresaService } from '../service/endereco-empresa.service';
import { EnderecoEmpresaFormService } from './endereco-empresa-form.service';

import { EnderecoEmpresaUpdateComponent } from './endereco-empresa-update.component';

describe('EnderecoEmpresa Management Update Component', () => {
  let comp: EnderecoEmpresaUpdateComponent;
  let fixture: ComponentFixture<EnderecoEmpresaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let enderecoEmpresaFormService: EnderecoEmpresaFormService;
  let enderecoEmpresaService: EnderecoEmpresaService;
  let empresaService: EmpresaService;
  let cidadeService: CidadeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [EnderecoEmpresaUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(EnderecoEmpresaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EnderecoEmpresaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    enderecoEmpresaFormService = TestBed.inject(EnderecoEmpresaFormService);
    enderecoEmpresaService = TestBed.inject(EnderecoEmpresaService);
    empresaService = TestBed.inject(EmpresaService);
    cidadeService = TestBed.inject(CidadeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Empresa query and add missing value', () => {
      const enderecoEmpresa: IEnderecoEmpresa = { id: 456 };
      const empresa: IEmpresa = { id: 13863 };
      enderecoEmpresa.empresa = empresa;

      const empresaCollection: IEmpresa[] = [{ id: 3640 }];
      jest.spyOn(empresaService, 'query').mockReturnValue(of(new HttpResponse({ body: empresaCollection })));
      const additionalEmpresas = [empresa];
      const expectedCollection: IEmpresa[] = [...additionalEmpresas, ...empresaCollection];
      jest.spyOn(empresaService, 'addEmpresaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ enderecoEmpresa });
      comp.ngOnInit();

      expect(empresaService.query).toHaveBeenCalled();
      expect(empresaService.addEmpresaToCollectionIfMissing).toHaveBeenCalledWith(
        empresaCollection,
        ...additionalEmpresas.map(expect.objectContaining),
      );
      expect(comp.empresasSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Cidade query and add missing value', () => {
      const enderecoEmpresa: IEnderecoEmpresa = { id: 456 };
      const cidade: ICidade = { id: 6219 };
      enderecoEmpresa.cidade = cidade;

      const cidadeCollection: ICidade[] = [{ id: 32319 }];
      jest.spyOn(cidadeService, 'query').mockReturnValue(of(new HttpResponse({ body: cidadeCollection })));
      const additionalCidades = [cidade];
      const expectedCollection: ICidade[] = [...additionalCidades, ...cidadeCollection];
      jest.spyOn(cidadeService, 'addCidadeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ enderecoEmpresa });
      comp.ngOnInit();

      expect(cidadeService.query).toHaveBeenCalled();
      expect(cidadeService.addCidadeToCollectionIfMissing).toHaveBeenCalledWith(
        cidadeCollection,
        ...additionalCidades.map(expect.objectContaining),
      );
      expect(comp.cidadesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const enderecoEmpresa: IEnderecoEmpresa = { id: 456 };
      const empresa: IEmpresa = { id: 21192 };
      enderecoEmpresa.empresa = empresa;
      const cidade: ICidade = { id: 16837 };
      enderecoEmpresa.cidade = cidade;

      activatedRoute.data = of({ enderecoEmpresa });
      comp.ngOnInit();

      expect(comp.empresasSharedCollection).toContain(empresa);
      expect(comp.cidadesSharedCollection).toContain(cidade);
      expect(comp.enderecoEmpresa).toEqual(enderecoEmpresa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEnderecoEmpresa>>();
      const enderecoEmpresa = { id: 123 };
      jest.spyOn(enderecoEmpresaFormService, 'getEnderecoEmpresa').mockReturnValue(enderecoEmpresa);
      jest.spyOn(enderecoEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ enderecoEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: enderecoEmpresa }));
      saveSubject.complete();

      // THEN
      expect(enderecoEmpresaFormService.getEnderecoEmpresa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(enderecoEmpresaService.update).toHaveBeenCalledWith(expect.objectContaining(enderecoEmpresa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEnderecoEmpresa>>();
      const enderecoEmpresa = { id: 123 };
      jest.spyOn(enderecoEmpresaFormService, 'getEnderecoEmpresa').mockReturnValue({ id: null });
      jest.spyOn(enderecoEmpresaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ enderecoEmpresa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: enderecoEmpresa }));
      saveSubject.complete();

      // THEN
      expect(enderecoEmpresaFormService.getEnderecoEmpresa).toHaveBeenCalled();
      expect(enderecoEmpresaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEnderecoEmpresa>>();
      const enderecoEmpresa = { id: 123 };
      jest.spyOn(enderecoEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ enderecoEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(enderecoEmpresaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEmpresa', () => {
      it('Should forward to empresaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(empresaService, 'compareEmpresa');
        comp.compareEmpresa(entity, entity2);
        expect(empresaService.compareEmpresa).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareCidade', () => {
      it('Should forward to cidadeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(cidadeService, 'compareCidade');
        comp.compareCidade(entity, entity2);
        expect(cidadeService.compareCidade).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
