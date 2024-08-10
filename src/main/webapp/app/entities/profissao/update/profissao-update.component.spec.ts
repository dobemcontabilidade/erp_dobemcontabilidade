import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ISocio } from 'app/entities/socio/socio.model';
import { SocioService } from 'app/entities/socio/service/socio.service';
import { ProfissaoService } from '../service/profissao.service';
import { IProfissao } from '../profissao.model';
import { ProfissaoFormService } from './profissao-form.service';

import { ProfissaoUpdateComponent } from './profissao-update.component';

describe('Profissao Management Update Component', () => {
  let comp: ProfissaoUpdateComponent;
  let fixture: ComponentFixture<ProfissaoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let profissaoFormService: ProfissaoFormService;
  let profissaoService: ProfissaoService;
  let socioService: SocioService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ProfissaoUpdateComponent],
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
      .overrideTemplate(ProfissaoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProfissaoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    profissaoFormService = TestBed.inject(ProfissaoFormService);
    profissaoService = TestBed.inject(ProfissaoService);
    socioService = TestBed.inject(SocioService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Socio query and add missing value', () => {
      const profissao: IProfissao = { id: 456 };
      const socio: ISocio = { id: 2622 };
      profissao.socio = socio;

      const socioCollection: ISocio[] = [{ id: 24606 }];
      jest.spyOn(socioService, 'query').mockReturnValue(of(new HttpResponse({ body: socioCollection })));
      const additionalSocios = [socio];
      const expectedCollection: ISocio[] = [...additionalSocios, ...socioCollection];
      jest.spyOn(socioService, 'addSocioToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ profissao });
      comp.ngOnInit();

      expect(socioService.query).toHaveBeenCalled();
      expect(socioService.addSocioToCollectionIfMissing).toHaveBeenCalledWith(
        socioCollection,
        ...additionalSocios.map(expect.objectContaining),
      );
      expect(comp.sociosSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const profissao: IProfissao = { id: 456 };
      const socio: ISocio = { id: 27503 };
      profissao.socio = socio;

      activatedRoute.data = of({ profissao });
      comp.ngOnInit();

      expect(comp.sociosSharedCollection).toContain(socio);
      expect(comp.profissao).toEqual(profissao);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProfissao>>();
      const profissao = { id: 123 };
      jest.spyOn(profissaoFormService, 'getProfissao').mockReturnValue(profissao);
      jest.spyOn(profissaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ profissao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: profissao }));
      saveSubject.complete();

      // THEN
      expect(profissaoFormService.getProfissao).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(profissaoService.update).toHaveBeenCalledWith(expect.objectContaining(profissao));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProfissao>>();
      const profissao = { id: 123 };
      jest.spyOn(profissaoFormService, 'getProfissao').mockReturnValue({ id: null });
      jest.spyOn(profissaoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ profissao: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: profissao }));
      saveSubject.complete();

      // THEN
      expect(profissaoFormService.getProfissao).toHaveBeenCalled();
      expect(profissaoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProfissao>>();
      const profissao = { id: 123 };
      jest.spyOn(profissaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ profissao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(profissaoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareSocio', () => {
      it('Should forward to socioService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(socioService, 'compareSocio');
        comp.compareSocio(entity, entity2);
        expect(socioService.compareSocio).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
