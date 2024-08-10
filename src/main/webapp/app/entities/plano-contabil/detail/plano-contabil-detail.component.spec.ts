import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { PlanoContabilDetailComponent } from './plano-contabil-detail.component';

describe('PlanoContabil Management Detail Component', () => {
  let comp: PlanoContabilDetailComponent;
  let fixture: ComponentFixture<PlanoContabilDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlanoContabilDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: PlanoContabilDetailComponent,
              resolve: { planoContabil: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(PlanoContabilDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PlanoContabilDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load planoContabil on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PlanoContabilDetailComponent);

      // THEN
      expect(instance.planoContabil()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
