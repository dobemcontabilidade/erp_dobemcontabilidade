import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { PermisaoService } from '../service/permisao.service';
import { IPermisao } from '../permisao.model';
import { PermisaoFormService } from './permisao-form.service';

import { PermisaoUpdateComponent } from './permisao-update.component';

describe('Permisao Management Update Component', () => {
  let comp: PermisaoUpdateComponent;
  let fixture: ComponentFixture<PermisaoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let permisaoFormService: PermisaoFormService;
  let permisaoService: PermisaoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PermisaoUpdateComponent],
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
      .overrideTemplate(PermisaoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PermisaoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    permisaoFormService = TestBed.inject(PermisaoFormService);
    permisaoService = TestBed.inject(PermisaoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const permisao: IPermisao = { id: 456 };

      activatedRoute.data = of({ permisao });
      comp.ngOnInit();

      expect(comp.permisao).toEqual(permisao);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPermisao>>();
      const permisao = { id: 123 };
      jest.spyOn(permisaoFormService, 'getPermisao').mockReturnValue(permisao);
      jest.spyOn(permisaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ permisao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: permisao }));
      saveSubject.complete();

      // THEN
      expect(permisaoFormService.getPermisao).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(permisaoService.update).toHaveBeenCalledWith(expect.objectContaining(permisao));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPermisao>>();
      const permisao = { id: 123 };
      jest.spyOn(permisaoFormService, 'getPermisao').mockReturnValue({ id: null });
      jest.spyOn(permisaoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ permisao: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: permisao }));
      saveSubject.complete();

      // THEN
      expect(permisaoFormService.getPermisao).toHaveBeenCalled();
      expect(permisaoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPermisao>>();
      const permisao = { id: 123 };
      jest.spyOn(permisaoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ permisao });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(permisaoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
